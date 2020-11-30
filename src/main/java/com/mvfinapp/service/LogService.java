package com.mvfinapp.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.mvfinapp.dto.MensagemErroDto;

@Service
public class LogService {

	public MensagemErroDto log(final Throwable cause, final HttpServletRequest req) {
		try {
			String mensagem = traceStack(cause).toString();

			return new MensagemErroDto(mensagem, req.getRequestURL().toString());

		} catch (Exception e) {
			return new MensagemErroDto(null, null);
		}
	}

	private StringBuilder traceStack(Throwable cause) {
		StringBuilder log = new StringBuilder();
		if (cause != null) {		
			log.append(cause.getMessage());
		}
		return log;
	}

}
