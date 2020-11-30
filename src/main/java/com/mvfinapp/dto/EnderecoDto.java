package com.mvfinapp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EnderecoDto {

	private Long idEndereco;
	private Long idCliente;
	private String uf;
	private String cidade;
	private String cep;
	private String bairro;
	private String logradouro;
	private String numero;
	private String complemento;
}
