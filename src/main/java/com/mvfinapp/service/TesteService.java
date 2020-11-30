package com.mvfinapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvfinapp.model.TesteModel;
import com.mvfinapp.repository.TesteRepository;

@Service
public class TesteService {

	@Autowired
	private TesteRepository testeRepository;
	
	
	public List<TesteModel> get() {
		return this.testeRepository.findAll();
	}
}
