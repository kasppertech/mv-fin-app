package com.mvfinapp.vo;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DashboardMovimentoVo {

	private Long qtdCliente;
	
	private Long qtdMovimento;

	private BigDecimal totalMovimentacao;

	private BigDecimal totalMovimentacaoSemana;

	private BigDecimal totalMovimentacaoMensal;

	private BigDecimal totalMovimentacaoAnual;

	private BigDecimal totalMovimentacaoUltimoAno;

}
