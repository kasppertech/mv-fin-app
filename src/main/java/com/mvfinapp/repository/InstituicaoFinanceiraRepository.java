package com.mvfinapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mvfinapp.model.InstituicaoFinanceiraModel;

@Repository
public interface InstituicaoFinanceiraRepository  extends JpaRepository<InstituicaoFinanceiraModel, Long> {

	@Query("select i from InstituicaoFinanceiraModel i where banco = :codigoBanco")
	InstituicaoFinanceiraModel findByBanco(String codigoBanco); 

}
