package com.mvfinapp.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mvfinapp.model.ClienteModel;
import com.mvfinapp.vo.ClienteVo;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {
 	
	@Query("SELECT new com.mvfinapp.vo.ClienteVo("
			+ " c.id,"			
			+ " c.telefone,"
			+ " c.email,"
			+ " c.nome,"
			+ " cpj.razaoSocial,"
			+ " cpf.cpf,"
			+ " cpj.cnpj"		
			+ " ) "
			+ " FROM ClienteModel c "
			+ " LEFT JOIN ClientePFModel cpf on c.id = cpf.cliente.id"
			+ " LEFT JOIN ClientePJModel cpj on c.id = cpj.cliente.id"				 
			+ " WHERE "
			+ " ("
			+ "  UPPER(TRIM(c.nome)) LIKE UPPER(CONCAT('%', :nome, '%'))"
			+ "  OR"
			+ "  UPPER(TRIM(cpj.razaoSocial)) LIKE UPPER(CONCAT('%', :nome, '%'))"
			+ " )"
			+ " AND ("
			+ "  UPPER(TRIM(cpf.cpf)) LIKE UPPER(CONCAT('%', :identificador, '%'))"
			+ "  OR "
			+ "  UPPER(TRIM(cpj.cnpj)) LIKE UPPER(CONCAT('%', :identificador, '%'))"
			+ " )"
			)
	Page<ClienteVo> getByFilter(@Param("nome") String nome, @Param("identificador") String identificador,Pageable page);

	
	@Query("SELECT new com.mvfinapp.vo.ClienteVo("
			+ " c.id,"			
			+ " c.telefone,"
			+ " c.email,"
			+ " c.nome,"
			+ " cpj.razaoSocial,"
			+ " cpf.cpf,"
			+ " cpj.cnpj"			
			+ " ) "
			+ " FROM ClienteModel c "
			+ " LEFT JOIN ClientePFModel cpf on c.id = cpf.cliente.id"
			+ " LEFT JOIN ClientePJModel cpj on c.id = cpj.cliente.id"					 
			+ " WHERE"
			+ " c.id = :idCliente "			 
			)
	Optional<ClienteVo> getClienteByIdCliente(long idCliente);
	
	@Query("SELECT c FROM ClienteModel c WHERE c.id = :idCliente")
	ClienteModel getClienteByIdClienteRetornaModel(long idCliente);
 
}
