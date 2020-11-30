package com.mvfinapp.service;

import java.util.Optional;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvfinapp.dto.ClienteDto;
import com.mvfinapp.model.ClienteModel;
import com.mvfinapp.model.ClientePFModel;
import com.mvfinapp.repository.ClientePFRepository;

@Service
public class ClientePFService {

	@Autowired
	private ClientePFRepository clientePFRepository;

	public void salvar(ClienteDto dto, ClienteModel cliente) {

		ClientePFModel model = new ClientePFModel();

		if (dto.getIdCliente() != null) {
			Optional<ClientePFModel> cl = this.clientePFRepository.findByCliente_Id(dto.getIdCliente());

			if (cl.isPresent()) {
				model = cl.get();
			}
		} else {
			Optional<ClientePFModel> cl = this.clientePFRepository.findByCpf(dto.getIdentificador());

			if (cl.isPresent()) {
				throw new ValidationException("CPF já cadastrado!");
			}
		}
		
		model.setCliente(cliente);
		model.setCpf(dto.getIdentificador());

		this.clientePFRepository.save(model);
	}

	public void deletarPorIdCliente(Long idCliente) {
		this.clientePFRepository.deleteByCliente_Id(idCliente);
	}
}
