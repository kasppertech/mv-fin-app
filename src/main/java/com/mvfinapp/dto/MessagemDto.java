package com.mvfinapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessagemDto {

	public MessagemDto(String mensagem) {
		this.mensagem = mensagem;
	}	
	
	private String mensagem;
}
