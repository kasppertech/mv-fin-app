package com.mvfinapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mvfinapp.dto.ContaDto;
import com.mvfinapp.model.ContaModel;

@Repository
public interface ContaRepository extends JpaRepository<ContaModel, Long> {

	@Query("select new com.mvfinapp.dto.ContaDto("
			+ " cc.id,"
			+ " cc.agencia,"
			+ " cc.agenciaDig,"
			+ " cc.conta,"
			+ " cc.contaDig"
			+ ") from ContaModel cc where cc.cliente.id = :idCliente ")
	List<ContaDto> findByClienteId(Long idCliente);

	@Query("SELECT c FROM ContaModel c "
			+ " WHERE "
			+ "     (CASE WHEN :agencia IS NULL OR TRIM(c.agencia) = :agencia THEN 1 ELSE 0 END) = 1"
			+ " AND (CASE WHEN :conta IS NULL OR TRIM(c.conta) = :conta THEN 1 ELSE 0 END) = 1")
	Page<ContaModel> findByFilter(@Param("agencia") String agencia, @Param("conta") String conta, Pageable page);

}
