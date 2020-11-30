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
@Table(name = "TB_CLIENTE_PF", schema = "MVFIN")
public class ClientePFModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5887763374604870137L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente")
	private ClienteModel cliente;
	
	@Column(name = "cpf")
	private String cpf;

}
