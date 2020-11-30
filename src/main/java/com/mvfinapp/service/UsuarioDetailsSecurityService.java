package com.mvfinapp.service;

import java.util.ArrayList;
import java.util.Optional;

import javax.validation.ValidationException;

import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mvfinapp.dto.JwtRequestDto;
import com.mvfinapp.model.UsuarioModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioDetailsSecurityService implements UserDetailsService {

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public UserDetails loadUserByUsername(final String usuario) {

		log.info("UsuarioDetailsSecurityService::loadUserByUsername::usuario: {}", usuario);

		User credential = null;

		Optional<UsuarioModel> usuarioModel = this.usuarioService.recuperarPorUsuario(usuario);

		if (usuarioModel.isEmpty()) {
			throw new UsernameNotFoundException("Usuário ou senha inválido(a)!");
		}

		credential = new User(usuarioModel.get().getUsuario(), usuarioModel.get().getSenha(), new ArrayList<>());

		log.info("UsuarioDetailsSecurityService::loadUserByUsername::User: {}", credential.toString());

		return credential;
	}

	public String consultaPorNome(String usuario) {

		log.info("UsuarioDetailsSecurityService::consultaByUsername::usuario: {}", usuario);

		Optional<UsuarioModel> usuarioModel = this.usuarioService.recuperarPorUsuario(usuario);

		String nome = "";
		if (usuarioModel.isPresent()) {
			nome = usuarioModel.get().getNome();
		}

		log.info("UsuarioDetailsSecurityService::consultaByUsername::nome: {}", nome);
		return nome;
	}

	public void validar(JwtRequestDto dto) {

		if (StringHelper.isEmpty(dto.getUsuario())) {
			throw new ValidationException("O campo usuario é obrigatório!");
		}

		if (StringHelper.isEmpty(dto.getSenha())) {
			throw new ValidationException("O campo senha é obrigatório!");
		}

	}

}
