package com.mvfinapp.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JwtResponseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3568165990135291778L;

	private final String jwtToken;

	private final String nome;

	public JwtResponseDto(String jwttoken, String nome) {
		this.jwtToken = jwttoken;
		this.nome = nome;
	}

}
