package com.mvfinapp.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ContaDto {
	
	public ContaDto() { }
	
	public ContaDto(Long id, String agencia, String agenciaDig, String conta,String contaDig) {
		
		this.id = id;
		this.agencia = agencia;
		this.agenciaDig = agenciaDig;
		this.conta = conta;
		this.contaDig = contaDig;
	}

	private Long id;
	private Long idCliente;
	private String agencia;
	private String agenciaDig;
	private String conta;
	private String contaDig;
	private String codigoBanco;
	private BigDecimal valor;
}
