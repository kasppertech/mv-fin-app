package com.mvfinapp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RelatorioSaldoClientePeriodoDto {

	private Long idCliente;
	
	private String dataInicial;
	
	private String dataFinal;
}
