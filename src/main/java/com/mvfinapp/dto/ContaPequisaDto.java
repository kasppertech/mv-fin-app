package com.mvfinapp.dto;

import com.mvfinapp.helper.lazyload.LazyLoadDto;

import lombok.Data;

@Data
public class ContaPequisaDto {
	
	private String agencia;
	private String conta;
	private LazyLoadDto lazyLoadDto;

}
