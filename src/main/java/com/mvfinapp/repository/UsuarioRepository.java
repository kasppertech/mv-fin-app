package com.mvfinapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mvfinapp.model.UsuarioModel;
 
@Repository 
public interface UsuarioRepository  extends JpaRepository<UsuarioModel, Long> {

	Optional<UsuarioModel> findByUsuario(String login);

}