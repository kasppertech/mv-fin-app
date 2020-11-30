package com.mvfinapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

 


@Entity
@Table(name = "TB_INSTITUICAO_FINANCEIRA", schema = "MVFIN")
public class InstituicaoFinanceiraModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2710942059136572029L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "banco")
	private String banco;
	
	@Column(name = "descricao")
	private String descricao;
}
