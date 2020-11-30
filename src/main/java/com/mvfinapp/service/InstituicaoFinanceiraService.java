package com.mvfinapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvfinapp.model.InstituicaoFinanceiraModel;
import com.mvfinapp.repository.InstituicaoFinanceiraRepository;

@Service
public class InstituicaoFinanceiraService {

	@Autowired
	private InstituicaoFinanceiraRepository instituicaoFinanceiraRepository;

	public InstituicaoFinanceiraModel consultaCodigoBanco(String codigoBanco) {
		 
		 return this.instituicaoFinanceiraRepository.findByBanco(codigoBanco);
	}
	
}
