package com.mvfinapp.vo;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TotalSaldoInicialVo {
	
	public TotalSaldoInicialVo(BigDecimal total) {
		this.total = total;
	}

	private BigDecimal total;
}
