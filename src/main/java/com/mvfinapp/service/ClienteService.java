package com.mvfinapp.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mvfinapp.dto.ClienteDto;
import com.mvfinapp.dto.ClientePequisaDto;
import com.mvfinapp.enumerator.TipoClienteType;
import com.mvfinapp.model.ClienteModel;
import com.mvfinapp.repository.ClienteRepository;
import com.mvfinapp.vo.ClienteCodigoVo;
import com.mvfinapp.vo.ClienteVo;
import com.mvfinapp.vo.ResultVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClienteService {

	private static final String REGISTRO_NAO_ENCONTRADO = "Registro(s) não encontrado(s)";

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ClientePFService clientePFService;

	@Autowired
	private ClientePJService clientePJService;

	public ResultVo<ClienteVo> consultaCliente(ClientePequisaDto dto) {

		log.info("ClienteService::consultaCliente::ClientePequisaDto: {}", dto);

		PageRequest page = PageRequest.of(dto.getLazyLoadDto().getPagina(), dto.getLazyLoadDto().getLinhas());

		Page<ClienteVo> lista = this.clienteRepository.getByFilter(dto.getNome(), dto.getIdentificador(), page);

		if (lista.getTotalElements() == 0) {
			throw new ValidationException(REGISTRO_NAO_ENCONTRADO);
		}

		log.info("ClienteService::consultaCliente::ClienteDto: {}", lista);
		return new ResultVo<>(dto.getLazyLoadDto(), lista.getContent(), BigInteger.valueOf(lista.getTotalElements()));

	}

	public ClienteVo consultaClientePorCodigo(long idCliente) {

		Optional<ClienteVo> cliente = this.clienteRepository.getClienteByIdCliente(idCliente);

		if (cliente.isEmpty()) {
			throw new ValidationException(REGISTRO_NAO_ENCONTRADO);
		}

		return cliente.get();
	}

	/**
	 * Cadu
	 * 
	 * @param idCliente
	 * @return
	 */
	public ClienteModel consultaClientePorCodigoRetModel(long idCliente) {

		ClienteModel model = this.clienteRepository.getClienteByIdClienteRetornaModel(idCliente);

		if (model == null) {
			throw new ValidationException(REGISTRO_NAO_ENCONTRADO);
		}

		return model;
	}

	@Transactional
	public ClienteCodigoVo salvarCliente(ClienteDto dto) {

		validacao(dto);

		ClienteModel cliente = salvar(dto);

		if (cliente.getTipoCliente().equals(TipoClienteType.JURIDICA.getValue().toString())) {
			this.clientePJService.salvar(dto, cliente);
		} else {
			this.clientePFService.salvar(dto, cliente);
		}

		return new ClienteCodigoVo(cliente.getId());

	}

	private void validacao(ClienteDto dto) {

		if (StringHelper.isEmpty(dto.getNome())) {
			throw new ValidationException("O campo nome é obrigatório!");
		}

		if (StringHelper.isEmpty(dto.getIdentificador())) {
			throw new ValidationException("O campo identificador(cpf/cnpj) é obrigatório!");
		}

		if (StringHelper.isEmpty(dto.getTelefone())) {
			throw new ValidationException("O campo telefone é obrigatório!");
		}

		if (StringHelper.isEmpty(dto.getEmail())) {
			throw new ValidationException("O campo email é obrigatório!");
		}

		if (dto.getIdentificador().length() > 11 && StringHelper.isEmpty(dto.getRazaoSocial())) {

			throw new ValidationException("O campo razão social é obrigatório!");
		}
	}

	private ClienteModel salvar(ClienteDto dto) {

		if (dto.getIdCliente() != null) {
			Optional<ClienteModel> cl = this.clienteRepository.findById(dto.getIdCliente());

			if (cl.isPresent()) {
				ClienteModel clienteModel = cl.get();
				clienteModel.setNome(dto.getNome());
				clienteModel.setTelefone(dto.getTelefone());
				clienteModel.setEmail(dto.getEmail());

				if (dto.getIdCliente() == null) {
					clienteModel.setDataCriacao(LocalDateTime.now());
				}

				if (dto.getIdentificador().length() > 11) {
					clienteModel.setTipoCliente(TipoClienteType.JURIDICA.getValue().toString());
				} else {
					clienteModel.setTipoCliente(TipoClienteType.FISICA.getValue().toString());
				}

				return this.clienteRepository.save(clienteModel);

			}
		}

		ClienteModel cliente = new ClienteModel(); 
		cliente.setNome(dto.getNome());
		cliente.setTelefone(dto.getTelefone());
		cliente.setEmail(dto.getEmail());
		cliente.setDataCriacao(LocalDateTime.now());

		if (dto.getIdentificador().length() > 11) {
			cliente.setTipoCliente(TipoClienteType.JURIDICA.getValue().toString());
		} else {
			cliente.setTipoCliente(TipoClienteType.FISICA.getValue().toString());
		}

		return this.clienteRepository.save(cliente);

	}

	@Transactional
	public void exclusaoClientePorCodigo(long idCliente) {

		Optional<ClienteModel> cliente = this.clienteRepository.findById(idCliente);

		if (cliente.isEmpty()) {
			throw new ValidationException(REGISTRO_NAO_ENCONTRADO);
		}

		if (cliente.get().getTipoCliente().equals(TipoClienteType.JURIDICA.getValue().toString())) {
			this.clientePJService.deletarPorIdCliente(cliente.get().getId());
		} else {
			this.clientePFService.deletarPorIdCliente(cliente.get().getId());
		}

		this.clienteRepository.delete(cliente.get());
	}

	public List<ClienteModel> consultaClienteRetModel() {

		List<ClienteModel> lista = this.clienteRepository.findAll();

		if (lista.isEmpty()) {
			throw new ValidationException(REGISTRO_NAO_ENCONTRADO);
		}

		return lista;
	}

	public Long consultaQuantidadeCliete() {		 
		return this.clienteRepository.count();
	}

}
