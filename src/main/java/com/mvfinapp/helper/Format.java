package com.mvfinapp.helper;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.text.MaskFormatter;

public class Format {

	private Format() {
		throw new IllegalStateException("Ocorreu um erro na formatação");
	}

	public static String formatCnpj(String cnpj, String mascara) throws ParseException {

		MaskFormatter mask = new MaskFormatter(mascara);
		mask.setValueContainsLiteralCharacters(false);
		return mask.valueToString(cnpj);
	}

	public static String formatLocalDateTimeDDMMYYYYHHMMSS(LocalDateTime data) {

		return data == null ? "" : data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
	}

	public static String formatLocalDateTimeDDMMYYYY(LocalDateTime data) {

		return data == null ? "" : data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

	public static String formatBigDecimal(BigDecimal valor) {

		if (valor != null) {
			Locale meuLocal = new Locale("pt", "BR");
			NumberFormat nfVal = NumberFormat.getCurrencyInstance(meuLocal);

			return nfVal.format(valor);
		}

		return "";
	}

}
