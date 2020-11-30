package com.mvfinapp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MensagemErroDto {

	public MensagemErroDto(String descricao,String url) {
		this.descricao = descricao;
		this.url = url;
	}

	private String descricao;

	private String url; 
}
