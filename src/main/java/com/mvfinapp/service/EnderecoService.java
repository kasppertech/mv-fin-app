package com.mvfinapp.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ValidationException;

import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mvfinapp.dto.EnderecoDto;
import com.mvfinapp.dto.EnderecoPequisaDto;
import com.mvfinapp.model.EnderecoModel;
import com.mvfinapp.repository.EnderecoRepository;
import com.mvfinapp.vo.EnderecoVo;
import com.mvfinapp.vo.ResultVo;

@Service
public class EnderecoService {

	private static final String REGISTRO_NAO_ENCONTRADO = "Registro não encontrado!";

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private ClienteService clienteService;

	public ResultVo<EnderecoVo> consultaEndereco(EnderecoPequisaDto dto) {

		PageRequest page = PageRequest.of(dto.getLazyLoadDto().getPagina(), dto.getLazyLoadDto().getLinhas());

		Page<EnderecoModel> listEndereco = this.enderecoRepository.findByFilter(dto.getIdCliente(), dto.getLogradouro(),
				page);
		if (listEndereco.isEmpty()) {
			throw new ValidationException(REGISTRO_NAO_ENCONTRADO);
		}

		List<EnderecoVo> converteModelToVo = converteModelToVo(listEndereco.getContent());
		return new ResultVo<>(dto.getLazyLoadDto(), converteModelToVo,
				BigInteger.valueOf(listEndereco.getTotalElements()));

	}

	private List<EnderecoVo> converteModelToVo(List<EnderecoModel> listEndereco) {

		List<EnderecoVo> listVo = new ArrayList<>();
		for (EnderecoModel model : listEndereco) {
			EnderecoVo vo = new EnderecoVo();

			vo.setBairro(model.getBairro());
			vo.setCep(model.getCep());
			vo.setCidade(model.getCidade());
			vo.setComplemento(model.getComplemento());
			vo.setIdCliente(model.getCliente().getId());
			vo.setIdEndereco(model.getId());
			vo.setLogradouro(model.getLogradouro());
			vo.setNumero(model.getNumero());
			vo.setUf(model.getUf());

			listVo.add(vo);
		}

		return listVo;

	}

	public void salvarEndereco(List<EnderecoDto> dto) {
		validacao(dto);

		for (EnderecoDto enderecoDto : dto) {
			EnderecoModel model = new EnderecoModel();
			if (enderecoDto.getIdEndereco() != null) {
				model = this.enderecoRepository.findById(enderecoDto.getIdEndereco()).orElse(null);
				if (model == null) {
					throw new ValidationException(REGISTRO_NAO_ENCONTRADO);
				}
			}

			model.setBairro(enderecoDto.getBairro());
			model.setCep(enderecoDto.getCep());
			model.setCidade(enderecoDto.getCidade());
			model.setCliente(this.clienteService.consultaClientePorCodigoRetModel(enderecoDto.getIdCliente()));
			model.setComplemento(enderecoDto.getComplemento());
			model.setLogradouro(enderecoDto.getLogradouro());
			model.setNumero(enderecoDto.getNumero());
			model.setUf(enderecoDto.getUf());
			this.enderecoRepository.save(model);
		}

	}

	public void validacao(List<EnderecoDto> dto) {

		for (EnderecoDto enderecoDto : dto) {
			if (StringHelper.isEmpty(enderecoDto.getBairro())) {
				throw new ValidationException("O campo bairro é obrigatório!");
			}

			if (StringHelper.isEmpty(enderecoDto.getCep())) {
				throw new ValidationException("O campo cep é obrigatório!");
			}

			if (StringHelper.isEmpty(enderecoDto.getCidade())) {
				throw new ValidationException("O campo cidade é obrigatório!");
			}

			if (StringHelper.isEmpty(enderecoDto.getLogradouro())) {
				throw new ValidationException("O campo logradouro é obrigatório!");
			}

			if (StringHelper.isEmpty(enderecoDto.getNumero())) {
				throw new ValidationException("O campo número é obrigatório!");
			}

			if (StringHelper.isEmpty(enderecoDto.getUf())) {
				throw new ValidationException("O campo UF é obrigatório!");
			}

			if (enderecoDto.getIdCliente() == null) {
				throw new ValidationException("Informe o código do cliente!");
			}
		}

	}

	public void deletarEndereco(Long id) {

		EnderecoModel model = this.enderecoRepository.findById(id).orElse(null);
		if (model == null) {
			throw new ValidationException(REGISTRO_NAO_ENCONTRADO);
		}

		this.enderecoRepository.deleteById(id);
	}

	public Optional<EnderecoModel> consultaEnderecoPorCliente(Long idCliente) {

		return this.enderecoRepository.findByCliente_Id(idCliente);

	}
}
