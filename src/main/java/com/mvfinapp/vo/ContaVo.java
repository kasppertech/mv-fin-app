package com.mvfinapp.vo;

import lombok.Data;

@Data
public class ContaVo {

	private Long idConta;
	private Long idCliente;
	private String nomeCliente;
	private String agencia;
	private String agenciaDig;
	private String conta;
	private String contaDig;

}
