package com.mvfinapp.service;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvfinapp.dto.ClienteDto;
import com.mvfinapp.model.ClienteModel;
import com.mvfinapp.model.ClientePJModel;
import com.mvfinapp.repository.ClientePJRepository;

@Service
public class ClientePJService {

	@Autowired
	private ClientePJRepository clientePJRepository;

	@Transactional
	public void salvar(ClienteDto dto, ClienteModel cliente) {

			ClientePJModel model = new ClientePJModel();

			if (dto.getIdCliente() != null) {
				Optional<ClientePJModel> cl = this.clientePJRepository.findByCliente_Id(dto.getIdCliente());

				if (cl.isPresent()) {
					model = cl.get();
				}
			} else {
				Optional<ClientePJModel> cl = this.clientePJRepository.findByCnpj(dto.getIdentificador());

				if (cl.isPresent()) {
					throw new ValidationException("CNPJ já cadastrado!");
				}
			}

			model.setInscricaoEstadual(dto.getInscricaoEstadual());
			model.setCliente(cliente);
			model.setCnpj(dto.getIdentificador());
			model.setRazaoSocial(dto.getRazaoSocial());

			this.clientePJRepository.save(model);
		
	}

	public void deletarPorIdCliente(Long idCliente) {

		this.clientePJRepository.deleteByCliente_Id(idCliente);

	}
}
