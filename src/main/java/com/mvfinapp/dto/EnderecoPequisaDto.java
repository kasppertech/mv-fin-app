package com.mvfinapp.dto;

import com.mvfinapp.helper.lazyload.LazyLoadDto;

import lombok.Data;

@Data
public class EnderecoPequisaDto {
		 
	private Long idCliente; 
	private String logradouro;
	private LazyLoadDto lazyLoadDto;

}
