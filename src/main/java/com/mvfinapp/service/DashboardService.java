package com.mvfinapp.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import com.mvfinapp.vo.DashboardMovimentoVo;

@Service
public class DashboardService {

	private static final String MVFIN = "mvfin";
	private static final String VALOR = "VALOR";
	private static final String PROCEDURE_CONSULTA_QUANTIDADE_CLIENTE = "PROCEDURE_CONSULTA_QUANTIDADE_CLIENTE";
	private static final String PROCEDURE_CONSULTA_QUANTIDADE_MOVIMENTACAO = "PROCEDURE_CONSULTA_QUANTIDADE_MOVIMENTACAO";
	private static final String PROCEDURE_CONSULTA_TOTAL_MOVIMENTACAO = "PROCEDURE_CONSULTA_TOTAL_MOVIMENTACAO";
	private static final String PROCEDURE_CONSULTA_TOTAL_MOVIMENTACAO_SEMANA = "PROCEDURE_CONSULTA_TOTAL_MOVIMENTACAO_SEMANA";
	private static final String PROCEDURE_CONSULTA_TOTAL_MOVIMENTACAO_MENSAL = "PROCEDURE_CONSULTA_TOTAL_MOVIMENTACAO_MENSAL";
	private static final String PROCEDURE_CONSULTA_TOTAL_MOVIMENTACAO_ANO = "PROCEDURE_CONSULTA_TOTAL_MOVIMENTACAO_ANO";

	@Autowired
	private JdbcTemplate template;

	public DashboardMovimentoVo dashboardMovimento() {

		DashboardMovimentoVo vo = new DashboardMovimentoVo();

		SimpleJdbcCall qtdCliente = new SimpleJdbcCall(template).withSchemaName(MVFIN)
				.withProcedureName(PROCEDURE_CONSULTA_QUANTIDADE_CLIENTE);
		SqlParameterSource qtdClienteSource = new MapSqlParameterSource();
		Map<String, Object> qtdClienteMap = qtdCliente.execute(qtdClienteSource);
		vo.setQtdCliente(Long.valueOf(qtdClienteMap.get("QUANTIDADE").toString()));

		SimpleJdbcCall qtdMov = new SimpleJdbcCall(template).withSchemaName(MVFIN)
				.withProcedureName(PROCEDURE_CONSULTA_QUANTIDADE_MOVIMENTACAO);
		SqlParameterSource qtdMovSource = new MapSqlParameterSource();
		Map<String, Object> qtdMovMap = qtdMov.execute(qtdMovSource);
		vo.setQtdMovimento(Long.valueOf(qtdMovMap.get("QUANTIDADE").toString()));

		SimpleJdbcCall qtdTotalMov = new SimpleJdbcCall(template).withSchemaName(MVFIN)
				.withProcedureName(PROCEDURE_CONSULTA_TOTAL_MOVIMENTACAO);
		SqlParameterSource qtdTotalMovSource = new MapSqlParameterSource();
		Map<String, Object> qtdTotalMovMap = qtdTotalMov.execute(qtdTotalMovSource);
		vo.setTotalMovimentacao(qtdTotalMovMap.get(VALOR) == null ? BigDecimal.ZERO
				: new BigDecimal(qtdTotalMovMap.get(VALOR).toString()));

		SimpleJdbcCall qtdMovSemana = new SimpleJdbcCall(template).withSchemaName(MVFIN)
				.withProcedureName(PROCEDURE_CONSULTA_TOTAL_MOVIMENTACAO_SEMANA);
		SqlParameterSource qtdMovSemanaSource = new MapSqlParameterSource();
		Map<String, Object> qtdMovSemanaMap = qtdMovSemana.execute(qtdMovSemanaSource);
		vo.setTotalMovimentacaoSemana(qtdMovSemanaMap.get(VALOR) == null ? BigDecimal.ZERO
				: new BigDecimal(qtdMovSemanaMap.get(VALOR).toString()));

		SimpleJdbcCall qtdMovMensal = new SimpleJdbcCall(template).withSchemaName(MVFIN)
				.withProcedureName(PROCEDURE_CONSULTA_TOTAL_MOVIMENTACAO_MENSAL);
		Map<String, Object> qtdMovInputParam = new HashMap<>();
		qtdMovInputParam.put("mes", LocalDate.now().getMonthValue());
		qtdMovInputParam.put("ano", LocalDate.now().getYear());
		SqlParameterSource qtdMovMensalSource = new MapSqlParameterSource(qtdMovInputParam);
		Map<String, Object> qtdMovMensalMap = qtdMovMensal.execute(qtdMovMensalSource);
		vo.setTotalMovimentacaoMensal(qtdMovMensalMap.get(VALOR) == null ? BigDecimal.ZERO
				: new BigDecimal(qtdMovMensalMap.get(VALOR).toString()));

		SimpleJdbcCall qtdTotalMovAno = new SimpleJdbcCall(template).withSchemaName(MVFIN)
				.withProcedureName(PROCEDURE_CONSULTA_TOTAL_MOVIMENTACAO_ANO);
		Map<String, Object> qtdTotalMovAnoInputParam = new HashMap<>();
		qtdTotalMovAnoInputParam.put("ano", LocalDate.now().getYear());
		SqlParameterSource qtdTotalMovAnoSource = new MapSqlParameterSource(qtdTotalMovAnoInputParam);
		Map<String, Object> qtdTotalMovAnoMap = qtdTotalMovAno.execute(qtdTotalMovAnoSource);
		vo.setTotalMovimentacaoAnual(qtdTotalMovAnoMap.get(VALOR) == null ? BigDecimal.ZERO
				: new BigDecimal(qtdTotalMovAnoMap.get(VALOR).toString()));

		SimpleJdbcCall qtdTotalMovAnoRet = new SimpleJdbcCall(template).withSchemaName(MVFIN)
				.withProcedureName(PROCEDURE_CONSULTA_TOTAL_MOVIMENTACAO_ANO);
		Map<String, Object> qtdTotalMovAnoRetInputParam = new HashMap<>();
		qtdTotalMovAnoRetInputParam.put("ano", LocalDate.now().getYear() - 1);
		SqlParameterSource qtdTotalMovAnoRetSource = new MapSqlParameterSource(qtdTotalMovAnoRetInputParam);
		Map<String, Object> qtdTotalMovAnoRetMap = qtdTotalMovAnoRet.execute(qtdTotalMovAnoRetSource);
		vo.setTotalMovimentacaoUltimoAno(qtdTotalMovAnoRetMap.get(VALOR) == null ? BigDecimal.ZERO
				: new BigDecimal(qtdTotalMovAnoRetMap.get(VALOR).toString()));

		return vo;
	}

}
