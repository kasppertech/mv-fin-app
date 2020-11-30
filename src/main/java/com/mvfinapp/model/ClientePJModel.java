package com.mvfinapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TB_CLIENTE_PJ", schema = "MVFIN")
public class ClientePJModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3559081976714783353L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente")
	private ClienteModel cliente;
	
	@Column(name = "razao_social")
	private String razaoSocial;
	
	@Column(name = "cnpj")
	private String cnpj;
	
	@Column(name = "inscricao_estadual")
	private String inscricaoEstadual;

}
