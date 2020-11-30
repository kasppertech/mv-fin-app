package com.mvfinapp.dto;

import com.mvfinapp.helper.lazyload.LazyLoadDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClientePequisaDto {
	
	private String nome;
	private String identificador;
	private LazyLoadDto lazyLoadDto;
}
