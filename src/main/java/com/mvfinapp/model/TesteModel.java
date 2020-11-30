package com.mvfinapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_PESSOA") 
public class TesteModel implements Serializable{
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1561708831472870620L;

	@Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") 
    private Long id;
 
    @Column(name = "nome") 
    private String nome;
 
 
 
}
