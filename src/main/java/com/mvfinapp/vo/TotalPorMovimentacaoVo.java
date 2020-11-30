package com.mvfinapp.vo;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TotalPorMovimentacaoVo {
	
	public TotalPorMovimentacaoVo(String tipo, BigDecimal total) {

		if(tipo.equals("CREDITO")) {
			this.tipo = "Movimentações de crédito";
		}
		
		if(tipo.equals("DEBITO")) {
			this.tipo = "Movimentações de débito";
		}
		
		this.total = total;
	}	

	private String tipo;
	
	private BigDecimal total;
}
