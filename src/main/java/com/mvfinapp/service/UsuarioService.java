package com.mvfinapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvfinapp.model.UsuarioModel;
import com.mvfinapp.repository.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Optional<UsuarioModel> recuperarPorUsuario(final String usuario) {
		log.info("UsuarioService::recuperarPorUsuario::usuario: {}", usuario);
		return this.usuarioRepository.findByUsuario(usuario);
	}
}
