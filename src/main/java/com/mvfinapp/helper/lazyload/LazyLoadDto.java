package com.mvfinapp.helper.lazyload;

import java.math.BigInteger;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LazyLoadDto {

	public LazyLoadDto() {

	}

	public LazyLoadDto(int pagina, int linhas) {

		this.pagina = pagina;
		this.linhas = linhas;
	}

	private int pagina;
	private int linhas;
	private BigInteger totalRegistros;

}
