package com.mvfinapp.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JwtRequestDto implements Serializable {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 4817912561282514905L;

	private String usuario;

	private String senha;

}