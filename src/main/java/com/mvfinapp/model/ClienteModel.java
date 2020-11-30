package com.mvfinapp.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TB_CLIENTE", schema = "MVFIN")
public class ClienteModel implements Serializable {

 

	/**
	 * 
	 */
	private static final long serialVersionUID = -4399179611793518756L;

	@Id	 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "id")
	private Long id;
 
	@Column(name = "telefone")
	private String telefone;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "tipo_cliente")
	private String tipoCliente;
		
	@Column(name = "data_criacao")
	private LocalDateTime dataCriacao;

}
