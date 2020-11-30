package com.mvfinapp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TB_MOVIMENTACAO", schema = "MVFIN")
public class MovimentacaoModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4819694191042894866L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
 	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente")
	private ClienteModel cliente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_conta")
	private ContaModel conta;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_instituicao_financeira")
	private InstituicaoFinanceiraModel instituicaoFinanceiraModel;
	
	@Column(name = "tipo_movimentacao")
	private String tipoMovimentacao;
	
	@Column(name = "tipo_transacao")
	private String tipoTransacao;
	
	@Column(name = "data_movimento")
	private LocalDateTime dataMovimento;
	
	@Column(name = "valor")
	private BigDecimal valor; 

}
