package com.mvfinapp.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClienteCodigoVo {

	public ClienteCodigoVo(Long idCliente) {
		this.idCliente = idCliente;

	}

	private Long idCliente;
}
