package com.mvfinapp.model;

import java.io.Serializable;
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
@Table(name = "TB_CONTA", schema = "MVFIN")
public class ContaModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3933740191752617451L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente")
	private ClienteModel cliente;

	@Column(name = "agencia")
	private String agencia;

	@Column(name = "agencia_dig")
	private String agenciaDig;

	@Column(name = "conta")
	private String conta;

	@Column(name = "conta_dig")
	private String contaDig;

	@Column(name = "data_exclusao")
	private LocalDateTime dataExclusao;

}
