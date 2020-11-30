package com.mvfinapp.resource;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvfinapp.dto.JwtRequestDto;
import com.mvfinapp.dto.JwtResponseDto;
import com.mvfinapp.dto.MessagemDto;
import com.mvfinapp.security.JwtTokenUtil;
import com.mvfinapp.service.UsuarioDetailsSecurityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
@Api(tags = { "Autenticação" })
public class AutenticacaoResource extends BaseResource {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

	@Autowired
	private UsuarioDetailsSecurityService usuarioDetailsSecurityService;

	@ApiResponse(code = 200, message = "Token de acesso gerado com sucesso.")
	@ApiOperation(value = "Autenticação", response = JwtResponseDto.class, notes = "Essa operação retorna o token de acesso.")
	@PostMapping(value = "/token")
	public ResponseEntity<Object> autenticacao(
			@RequestBody @ApiParam(value = "Dto da requesição de autenticação.", required = true) JwtRequestDto dto,
			HttpServletRequest req) {

		try {

			log.info("AutenticacaoResource::autenticacao::JwtRequestDto: {}", dto.toString());

			this.usuarioDetailsSecurityService.validar(dto);

			authenticate(dto.getUsuario(), dto.getSenha());

			final UserDetails userDetails = jwtInMemoryUserDetailsService.loadUserByUsername(dto.getUsuario());

			final String token = jwtTokenUtil.generateToken(userDetails);

			final String nome = this.usuarioDetailsSecurityService.consultaPorNome(dto.getUsuario());

			JwtResponseDto jwtResponse = new JwtResponseDto(token, nome);

			log.info("AutenticacaoResource::autenticacao::JwtResponseDto: {}", jwtResponse.toString());

			return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
		} catch (BadCredentialsException e) {

			String texto = "Usuário ou senha inválido(a)!";
			log.error("AutenticacaoResource::autenticacao::mensagem: {} :Exception: {}", texto, e.toString());
			return new ResponseEntity<>(new MessagemDto(texto), HttpStatus.UNAUTHORIZED);

		} catch (ValidationException e) {

			log.error("ClienteResource::consultaCliente::mensagem: {}", e.getMessage());
			return new ResponseEntity<>(new MessagemDto(e.getMessage()), HttpStatus.CONFLICT);

		} catch (Exception e) {

			log.error("AutenticacaoResource::autenticacao::Exception: {}", e.toString());
			return new ResponseEntity<>(super.logService.log(e, req), HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	private void authenticate(String username, String password) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

	}
}
