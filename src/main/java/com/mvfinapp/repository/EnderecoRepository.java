package com.mvfinapp.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mvfinapp.model.EnderecoModel;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoModel, Long> {

	@Query("SELECT e FROM EnderecoModel e WHERE "
			+ " (CASE WHEN :logradouro IS NULL OR e.logradouro like :logradouro THEN 1 ELSE 0 END) = 1"
			+ " AND (CASE WHEN :idCliente IS NULL OR e.cliente.id = :idCliente THEN 1 ELSE 0 END) = 1")
	Page<EnderecoModel> findByFilter(@Param("idCliente") Long idCliente, @Param("logradouro") String logradouro,
			Pageable page);

	Optional<EnderecoModel> findByCliente_Id(Long idCliente);

}
