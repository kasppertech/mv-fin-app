package com.mvfinapp.vo;

import lombok.Data;

@Data
public class EnderecoVo {

	private Long idEndereco;
	private Long idCliente;
	private String nomeCliente;
	private String uf;
	private String cidade;
	private String cep;
	private String bairro;
	private String logradouro;
	private String numero;
	private String complemento;
	
}
