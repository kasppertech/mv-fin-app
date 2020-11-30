package com.mvfinapp.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mvfinapp.dto.ContaDto;
import com.mvfinapp.dto.ContaPequisaDto;
import com.mvfinapp.model.ContaModel;
import com.mvfinapp.model.MovimentacaoModel;
import com.mvfinapp.repository.ContaRepository;
import com.mvfinapp.vo.ContaVo;
import com.mvfinapp.vo.ResultVo;

@Service
public class ContaService {

	private static final String REGISTRO_NAO_ENCONTRADO = "Registro não encontrado!";
	private static final String CONTA_NAO_PODE_SER_EXCLUIDA = "A Conta não pode ser excluida, existe movimentação para essa Conta";

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private MovimentacaoService movimentacaoService;

	@Autowired
	private InstituicaoFinanceiraService instituicaoFinanceiraService;

	public List<ContaDto> consultaContaPorCliente(Long idCliente) {
		return this.contaRepository.findByClienteId(idCliente);
	}

	public ResultVo<ContaVo> consultaConta(ContaPequisaDto dto) {

		PageRequest page = PageRequest.of(dto.getLazyLoadDto().getPagina(), dto.getLazyLoadDto().getLinhas());

		Page<ContaModel> listConta = this.contaRepository.findByFilter(dto.getAgencia(), dto.getConta(), page);

		if (listConta.isEmpty()) {
			throw new ValidationException(REGISTRO_NAO_ENCONTRADO);
		}

		List<ContaVo> converteModelToVo = converteModelToVo(listConta.getContent());
		return new ResultVo<>(dto.getLazyLoadDto(), converteModelToVo,
				BigInteger.valueOf(listConta.getTotalElements()));
	}

	private List<ContaVo> converteModelToVo(List<ContaModel> listConta) {

		List<ContaVo> listVo = new ArrayList<>();
		for (ContaModel model : listConta) {
			ContaVo vo = new ContaVo();
			vo.setAgencia(model.getAgencia().trim());
			vo.setAgenciaDig(model.getAgenciaDig().trim());
			vo.setConta(model.getConta().trim());
			vo.setContaDig(model.getContaDig().trim());
			vo.setIdCliente(model.getCliente().getId());
			vo.setIdConta(model.getId());

			listVo.add(vo);
		}

		return listVo;
	}

	@Transactional
	public void salvarConta(List<ContaDto> dto) {

		validacao(dto);

		for (ContaDto contaDto : dto) {
			ContaModel model = new ContaModel();
			if (contaDto.getId() != null) {
				model = this.contaRepository.findById(contaDto.getId()).orElse(null);
				if (model == null) {
					throw new ValidationException(REGISTRO_NAO_ENCONTRADO);
				} else {
					if (!this.movimentacaoService.consultaMovimentacaoPorIdConta(model.getId()).isEmpty()) {
						throw new ValidationException(CONTA_NAO_PODE_SER_EXCLUIDA);
					}
				}
			}

			model.setAgencia(contaDto.getAgencia());
			model.setAgenciaDig(contaDto.getAgenciaDig());
			model.setCliente(this.clienteService.consultaClientePorCodigoRetModel(contaDto.getIdCliente()));
			model.setConta(contaDto.getConta());
			model.setContaDig(contaDto.getContaDig());

			this.contaRepository.save(model);

			if (contaDto.getValor() != null) {
				MovimentacaoModel movimentacaoModel = new MovimentacaoModel();

				movimentacaoModel
						.setCliente(this.clienteService.consultaClientePorCodigoRetModel(contaDto.getIdCliente()));
				movimentacaoModel.setInstituicaoFinanceiraModel(
						this.instituicaoFinanceiraService.consultaCodigoBanco(contaDto.getCodigoBanco()));
				movimentacaoModel.setConta(model);
				movimentacaoModel.setDataMovimento(LocalDateTime.now());
				movimentacaoModel.setTipoMovimentacao("CREDITO");
				movimentacaoModel.setTipoTransacao("RECEBIMENTOS");
				movimentacaoModel.setValor(contaDto.getValor());
				
				this.movimentacaoService.salvar(movimentacaoModel);

			}

		}
 
	}

	public void validacao(List<ContaDto> dto) {

		for (ContaDto contaDto : dto) {
			if (StringHelper.isEmpty(contaDto.getAgencia())) {
				throw new ValidationException("O campo agência é obrigatório!");
			}

			if (StringHelper.isEmpty(contaDto.getConta())) {
				throw new ValidationException("O campo conta é obrigatório!");
			}
		} 
 
	}

	public void deletarConta(Long id) {

		ContaModel model = this.contaRepository.findById(id).orElse(null);
		if (model == null) {
			throw new ValidationException(REGISTRO_NAO_ENCONTRADO);
		} else {
			if (!this.movimentacaoService.consultaMovimentacaoPorIdConta(id).isEmpty()) {
				throw new ValidationException(CONTA_NAO_PODE_SER_EXCLUIDA);
			} else {
				this.contaRepository.deleteById(id);
			}
		}
	}
}
