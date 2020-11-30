package com.mvfinapp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClienteDto {

	private Long idCliente;
	private String nome;
	private String razaoSocial;
	private String inscricaoEstadual;
	private String identificador;
	private String telefone;
	private String email;	 
}
