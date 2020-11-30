package com.mvfinapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mvfinapp.model.ClientePFModel;

@Repository
public interface ClientePFRepository extends JpaRepository<ClientePFModel, Long> {

	Optional<ClientePFModel> findByCliente_Id(Long idCliente);

	Optional<ClientePFModel> findByCpf(String identificador);

	void deleteByCliente_Id(Long idCliente);

}
