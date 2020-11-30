package com.mvfinapp.enumerator;

import lombok.Getter;

@Getter
public enum TipoClienteType {

	JURIDICA(1, "Jurídica"), FISICA(2, "Física");

	public static TipoClienteType valueOf(final int value) {
		for (final TipoClienteType type : values()) {
			if (type.value == value) {
				return type;
			}
		}
		return TipoClienteType.FISICA;
	}

	private final Integer value;

	private final String text;

	TipoClienteType(final Integer value, final String text) {
		this.value = value;
		this.text = text;
	}

 
}
