package com.mvfinapp.vo;

import java.math.BigInteger;
import java.util.List;

import com.mvfinapp.helper.lazyload.LazyLoadDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultVo<T> {

	private LazyLoadDto lazyDto;

	private List<T> listVo; 

	public ResultVo(LazyLoadDto dto) {

		if (dto == null) {
			this.lazyDto = new LazyLoadDto();
			this.lazyDto.setLinhas(10);
			this.lazyDto.setPagina(0);
		} else {
			this.lazyDto = dto;
		}

		this.lazyDto.setTotalRegistros(BigInteger.TEN);
	}

	public ResultVo(LazyLoadDto dto, List<T> listVo, BigInteger totalRegistros) {
		if (dto == null) {
			this.lazyDto = new LazyLoadDto();
			this.lazyDto.setLinhas(10);
			this.lazyDto.setPagina(0);
		} else {
			this.lazyDto = dto;
		}
		this.lazyDto.setTotalRegistros(totalRegistros);
		this.listVo = listVo;
	}

}
