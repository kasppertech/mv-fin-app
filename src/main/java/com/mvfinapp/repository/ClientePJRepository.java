package com.mvfinapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mvfinapp.model.ClientePJModel;

 
@Repository
public interface ClientePJRepository extends JpaRepository<ClientePJModel, Long> {

	Optional<ClientePJModel> findByCnpj(String identificador);

	Optional<ClientePJModel> findByCliente_Id(Long idCliente);

	void deleteByCliente_Id(Long idCliente);

}
