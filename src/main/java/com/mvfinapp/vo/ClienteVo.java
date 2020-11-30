package com.mvfinapp.vo;

import java.text.ParseException;

import com.mvfinapp.helper.Format;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClienteVo {

	public ClienteVo() {

	}

	public ClienteVo(Long idCliente, String telefone, String email, String nome, String razaoSocial, String cpf,
			String cnpj) throws ParseException {
		this.idCliente = idCliente;
		this.nome = nome;

		if (cpf != null) {
			this.identificador = Format.formatCnpj(cpf, "###.###.###-##");
			this.tipo = "Física";
		}

		if (cnpj != null) {

			this.razaoSocial = razaoSocial;
			this.identificador = Format.formatCnpj(cnpj, "##.###.###/####-##");
			this.tipo = "Jurídica";
		}

		this.telefone = telefone;
		this.email = email;
	}

	private Long idCliente;
	private String nome;
	private String razaoSocial;
	private String identificador;
	private String telefone;
	private String email;
	private String tipo;

}
