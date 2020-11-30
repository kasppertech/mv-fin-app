package com.mvfinapp.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TotalReceitaEmpresaDto {

	public TotalReceitaEmpresaDto(String nome, Long quantidade, double valor) {
		this.nome = nome;
		this.quantidade = quantidade;
		this.valor = BigDecimal.valueOf(valor);
	}

	private String nome;

	private Long quantidade;

	private BigDecimal valor;
}
