package com.mvfinapp.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mvfinapp.dto.RelatorioReceitaEmpresaDto;
import com.mvfinapp.dto.RelatorioSaldoClientePeriodoDto;
import com.mvfinapp.dto.TotalReceitaEmpresaDto;
import com.mvfinapp.helper.Format;
import com.mvfinapp.model.ClienteModel;
import com.mvfinapp.model.EnderecoModel;
import com.mvfinapp.model.MovimentacaoModel;
import com.mvfinapp.repository.MovimentacaoRepository;
import com.mvfinapp.vo.ArquivoXlsVo;
import com.mvfinapp.vo.TotalPorMovimentacaoVo;
import com.mvfinapp.vo.TotalSaldoInicialVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MovimentacaoService {
	private static final String CLIENTE = "Cliente";
	private static final String CLIENTE_DESDE = "Cliente desde";
	private static final String RELATORIO = "Relatório";
	protected static final DateTimeFormatter YYYYMMDDHHMMSS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	protected static final String HORARIO_INICIO = " 00:00:00";
	protected static final String HORARIO_FINAL = " 23:59:59";

	@Autowired
	private MovimentacaoRepository movimentacaoRepository;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private EnderecoService enderecoService;

	public List<MovimentacaoModel> consultaMovimentacaoPorIdConta(Long id) {
		return this.movimentacaoRepository.consultaMovimentacaoPorIdConta(id);
	}

	public ArquivoXlsVo consultaSaldoCliente(Long idCliente) throws IOException {
		log.info("MovimentacaoService::consultaSaldoCliente::idCliente: {}", idCliente);

		ClienteModel cliente = this.clienteService.consultaClientePorCodigoRetModel(idCliente);

		Optional<EnderecoModel> endereroModel = this.enderecoService.consultaEnderecoPorCliente(cliente.getId());

		HSSFWorkbook relatorioXls = new HSSFWorkbook();
		HSSFSheet dadosRelatorio = relatorioXls.createSheet(RELATORIO);

		Row row = dadosRelatorio.createRow(0);
		Cell cc = row.createCell(0);

		// Cliente
		row = dadosRelatorio.createRow(0);

		criaColuna(CLIENTE, cc, 0, row);
		criaColuna(CLIENTE_DESDE, cc, 1, row);

		row = dadosRelatorio.createRow(1);

		criaColuna(cliente.getNome(), cc, 0, row);
		criaColuna(Format.formatLocalDateTimeDDMMYYYYHHMMSS(cliente.getDataCriacao()), cc, 1, row);

		// Endereço
		if (endereroModel.isPresent()) {
			endereco(endereroModel.get(), dadosRelatorio, cc, row, 3);
		}

		List<TotalPorMovimentacaoVo> listaTotalMovimentacao = this.movimentacaoRepository
				.totalMovimentacaoPorIdCliente(cliente.getId());

		int linha = 6;
		for (TotalPorMovimentacaoVo item : listaTotalMovimentacao) {

			row = dadosRelatorio.createRow(linha);

			criaColuna(item.getTipo(), cc, 0, row);
			criaColuna(Format.formatBigDecimal(item.getTotal()), cc, 1, row);
			linha += 1;
		}

		row = dadosRelatorio.createRow(8);

		criaColuna("Total de movimentações", cc, 0, row);

		if (listaTotalMovimentacao.isEmpty()) {

			criaColuna(Format.formatBigDecimal(BigDecimal.ZERO), cc, 1, row);
		} else {

			BigDecimal totalMovimentacao = listaTotalMovimentacao.stream()
					.map(x -> x.getTotal() == null ? BigDecimal.ZERO : x.getTotal())
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			criaColuna(Format.formatBigDecimal(totalMovimentacao), cc, 1, row);

		}

		BigDecimal totalPorMovimentacao = this.movimentacaoRepository
				.valorPagoPelasMovimentacaoPorIdCliente(cliente.getId());

		row = dadosRelatorio.createRow(9);

		criaColuna("Valor pago pelas movimentações", cc, 0, row);
		criaColuna(Format.formatBigDecimal(totalPorMovimentacao), cc, 1, row);

		Page<TotalSaldoInicialVo> totalSaldoInicial = this.movimentacaoRepository
				.saldoInicialPorIdCliente(cliente.getId(), PageRequest.of(0, 1));

		BigDecimal totalRecebimento = this.movimentacaoRepository.totalRecebimentoPorIdCliente(cliente.getId());

		BigDecimal totalPagamento = this.movimentacaoRepository.totalPagamentoPorIdCliente(cliente.getId());

		saldoInicial(totalSaldoInicial, dadosRelatorio, cc, row, 10);
		saldoContaAtual(totalRecebimento, totalPagamento, dadosRelatorio, cc, row, 11);

		autoSizeColuna(dadosRelatorio, 7);

		return gerarExcel(relatorioXls, "Relatório de saldo do cliente.xls");

	}

	private void autoSizeColuna(HSSFSheet dadosRelatorio, int totalColuna) {

		int colunaAutoSize = 0;
		while (colunaAutoSize < totalColuna) {
			dadosRelatorio.autoSizeColumn(colunaAutoSize);
			colunaAutoSize++;
		}
	}

	private void saldoInicial(Page<TotalSaldoInicialVo> totalSaldoInicial, HSSFSheet dadosRelatorio, Cell cc, Row row,
			int linha) {

		row = dadosRelatorio.createRow(linha);

		criaColuna("Saldo inicial", cc, 0, row);

		if (totalSaldoInicial.getTotalElements() > 0) {
			criaColuna(Format.formatBigDecimal(totalSaldoInicial.getContent().get(0).getTotal()), cc, 1, row);
		} else {
			criaColuna(Format.formatBigDecimal(BigDecimal.ZERO), cc, 1, row);
		}
	}

	private void saldoContaAtual(BigDecimal totalRecebimento, BigDecimal totalPagamento, HSSFSheet dadosRelatorio,
			Cell cc, Row row, int linha) {

		row = dadosRelatorio.createRow(linha);

		criaColuna("Saldo atual", cc, 0, row);

		if (totalRecebimento != null && totalPagamento != null) {

			BigDecimal somaSaldoAtual = totalRecebimento.subtract(totalPagamento);

			criaColuna(Format.formatBigDecimal(somaSaldoAtual), cc, 1, row);
		} else if (totalRecebimento == null && totalPagamento == null) {
			criaColuna(Format.formatBigDecimal(BigDecimal.ZERO), cc, 1, row);
		} else {

			if (totalRecebimento != null) {
				criaColuna(Format.formatBigDecimal(totalRecebimento), cc, 1, row);
			}

			if (totalPagamento != null) {
				criaColuna(Format.formatBigDecimal(totalPagamento), cc, 1, row);
			}

		}
	}

	private void endereco(EnderecoModel enderecoModel, HSSFSheet dadosRelatorio, Cell cc, Row row, int linha) {

		row = dadosRelatorio.createRow(linha);

		criaColuna("Logradouro", cc, 0, row);
		criaColuna("Número", cc, 1, row);
		criaColuna("Complemento", cc, 2, row);
		criaColuna("Bairro", cc, 3, row);
		criaColuna("Cidade", cc, 4, row);
		criaColuna("UF", cc, 5, row);
		criaColuna("CEP", cc, 6, row);

		linha += 1;
		row = dadosRelatorio.createRow(linha);

		criaColuna(enderecoModel.getLogradouro(), cc, 0, row);
		criaColuna(enderecoModel.getNumero(), cc, 1, row);
		criaColuna(enderecoModel.getComplemento(), cc, 2, row);
		criaColuna(enderecoModel.getBairro(), cc, 3, row);
		criaColuna(enderecoModel.getCidade(), cc, 4, row);
		criaColuna(enderecoModel.getUf(), cc, 5, row);
		criaColuna(enderecoModel.getCep(), cc, 6, row);
	}

	private void criaColuna(String textoColuna, Cell cc, int collumn, Row linha) {

		cc = linha.createCell(collumn);
		cc.setCellValue(textoColuna);

	}

	private ArquivoXlsVo gerarExcel(HSSFWorkbook relatorioXls, String nomeArquivo) throws IOException {
		ArquivoXlsVo arquivoXls = new ArquivoXlsVo();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		relatorioXls.write(bos);

		arquivoXls.setData(bos.toByteArray());
		arquivoXls.setNomeArquivo(nomeArquivo);

		return arquivoXls;
	}

	public ArquivoXlsVo consultaSaldoClientePorPeriodo(RelatorioSaldoClientePeriodoDto dto) throws IOException {

		log.info("MovimentacaoService::consultaSaldoClientePorPeriodo::RelatorioSaldoClientePeriodoDto: {}", dto);

		LocalDateTime dataInicial = LocalDateTime.parse(dto.getDataInicial() + HORARIO_INICIO, YYYYMMDDHHMMSS);
		LocalDateTime dataFim = LocalDateTime.parse(dto.getDataFinal() + HORARIO_FINAL, YYYYMMDDHHMMSS);

		ClienteModel cliente = this.clienteService.consultaClientePorCodigoRetModel(dto.getIdCliente());

		Optional<EnderecoModel> endereroModel = this.enderecoService.consultaEnderecoPorCliente(cliente.getId());

		HSSFWorkbook relatorioXls = new HSSFWorkbook();
		HSSFSheet dadosRelatorio = relatorioXls.createSheet(RELATORIO);

		Row row = dadosRelatorio.createRow(0);
		Cell cc = row.createCell(0);

		// Cliente

		row = dadosRelatorio.createRow(0);

		criaColuna("Periodo", cc, 0, row);
		criaColuna(
				Format.formatLocalDateTimeDDMMYYYY(dataInicial) + " a " + Format.formatLocalDateTimeDDMMYYYY(dataFim),
				cc, 1, row);

		row = dadosRelatorio.createRow(1);

		criaColuna(CLIENTE, cc, 0, row);
		criaColuna(CLIENTE_DESDE, cc, 1, row);

		row = dadosRelatorio.createRow(2);

		criaColuna(cliente.getNome(), cc, 0, row);
		criaColuna(Format.formatLocalDateTimeDDMMYYYYHHMMSS(cliente.getDataCriacao()), cc, 1, row);

		// Endereço
		if (endereroModel.isPresent()) {

			endereco(endereroModel.get(), dadosRelatorio, cc, row, 4);
		}

		List<TotalPorMovimentacaoVo> listaTotalMovimentacao = this.movimentacaoRepository
				.totalMovimentacaoPorIdClientePeriodo(cliente.getId(), dataInicial, dataFim);

		int linha = 7;
		for (TotalPorMovimentacaoVo item : listaTotalMovimentacao) {

			row = dadosRelatorio.createRow(linha);

			criaColuna(item.getTipo(), cc, 0, row);
			criaColuna(Format.formatBigDecimal(item.getTotal()), cc, 1, row);
			linha += 1;
		}

		row = dadosRelatorio.createRow(9);

		criaColuna("Total de movimentações", cc, 0, row);

		BigDecimal totalMovimentacao = listaTotalMovimentacao.stream()
				.map(x -> x.getTotal() == null ? BigDecimal.ZERO : x.getTotal())
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		criaColuna(Format.formatBigDecimal(totalMovimentacao), cc, 1, row);

		BigDecimal totalPorMovimentacao = this.movimentacaoRepository
				.valorPagoPelasMovimentacaoPorIdClientePeriodo(cliente.getId(), dataInicial, dataFim);

		row = dadosRelatorio.createRow(10);

		criaColuna("Valor pago pelas movimentações", cc, 0, row);
		criaColuna(Format.formatBigDecimal(totalPorMovimentacao), cc, 1, row);

		Page<TotalSaldoInicialVo> totalSaldoInicial = this.movimentacaoRepository
				.saldoInicialPorIdClientePeriodo(cliente.getId(), dataInicial, dataFim, PageRequest.of(0, 1));

		BigDecimal totalRecebimento = this.movimentacaoRepository.totalRecebimentoPorIdClientePeriodo(cliente.getId(),
				dataInicial, dataFim);

		BigDecimal totalPagamento = this.movimentacaoRepository.totalPagamentoPorIdClientePeriodo(cliente.getId(),
				dataInicial, dataFim);

		saldoInicial(totalSaldoInicial, dadosRelatorio, cc, row, 11);
		saldoContaAtual(totalRecebimento, totalPagamento, dadosRelatorio, cc, row, 12);

		autoSizeColuna(dadosRelatorio, 7);

		return gerarExcel(relatorioXls, "Relatório de saldo do cliente Período.xls");
	}

	public ArquivoXlsVo consultaSaldoTodosCliente() throws IOException {

		log.info("MovimentacaoService::consultaSaldoTodosCliente::RelatorioSaldoClientePeriodoDto");

		List<ClienteModel> listacliente = this.clienteService.consultaClienteRetModel();

		HSSFWorkbook relatorioXls = new HSSFWorkbook();
		HSSFSheet dadosRelatorio = relatorioXls.createSheet(RELATORIO);

		Row row = dadosRelatorio.createRow(0);
		Cell cc = row.createCell(0);

		// Cliente
		row = dadosRelatorio.createRow(1);

		criaColuna(CLIENTE, cc, 0, row);
		criaColuna(CLIENTE_DESDE, cc, 1, row);
		criaColuna("Saldo", cc, 2, row);

		int linha = 2;

		for (int i = 0; i < listacliente.size(); i++) {

			row = dadosRelatorio.createRow(linha + i);

			criaColuna(listacliente.get(i).getNome(), cc, 0, row);
			criaColuna(Format.formatLocalDateTimeDDMMYYYYHHMMSS(listacliente.get(i).getDataCriacao()), cc, 1, row);

			BigDecimal totalRecebimento = this.movimentacaoRepository
					.totalRecebimentoPorIdCliente(listacliente.get(i).getId());

			BigDecimal totalPagamento = this.movimentacaoRepository
					.totalPagamentoPorIdCliente(listacliente.get(i).getId());

			if (totalRecebimento != null && totalPagamento != null) {

				BigDecimal somaSaldoAtual = totalRecebimento.subtract(totalPagamento);

				criaColuna(Format.formatBigDecimal(somaSaldoAtual), cc, 2, row);
			} else if (totalRecebimento == null && totalPagamento == null) {
				criaColuna(Format.formatBigDecimal(BigDecimal.ZERO), cc, 2, row);
			} else {

				if (totalRecebimento != null) {
					criaColuna(Format.formatBigDecimal(totalRecebimento), cc, 2, row);
				}

				if (totalPagamento != null) {
					criaColuna(Format.formatBigDecimal(totalPagamento), cc, 2, row);
				}

			}

		}

		autoSizeColuna(dadosRelatorio, 3);

		return gerarExcel(relatorioXls, "Relatório de saldo todos os cliente.xls");
	}

	public ArquivoXlsVo consultaRelatorioReceitaEmpresasPeriodo(RelatorioReceitaEmpresaDto dto) throws IOException {

		log.info("MovimentacaoService::consultaSaldoTodosCliente::RelatorioReceitaEmpresaDto: {}", dto);

		LocalDateTime dataInicial = LocalDateTime.parse(dto.getDataInicial() + HORARIO_INICIO, YYYYMMDDHHMMSS);
		LocalDateTime dataFim = LocalDateTime.parse(dto.getDataFinal() + HORARIO_FINAL, YYYYMMDDHHMMSS);

		List<TotalReceitaEmpresaDto> lista = this.movimentacaoRepository.totalReceitaEmpresa(dataInicial, dataFim);

		HSSFWorkbook relatorioXls = new HSSFWorkbook();
		HSSFSheet dadosRelatorio = relatorioXls.createSheet(RELATORIO);

		Row row = dadosRelatorio.createRow(0);
		Cell cc = row.createCell(0);

		// Cliente

		row = dadosRelatorio.createRow(0);
//
		criaColuna("Período", cc, 0, row);
		criaColuna(
				Format.formatLocalDateTimeDDMMYYYY(dataInicial) + " a " + Format.formatLocalDateTimeDDMMYYYY(dataFim),
				cc, 1, row);
		row = dadosRelatorio.createRow(1);

		criaColuna(CLIENTE, cc, 0, row);
		criaColuna("Quantidade de movimentações", cc, 1, row);
		criaColuna("Valor das movimentações", cc, 2, row);

		int linha = 1;

		for (int i = 0; i < lista.size(); i++) {

			linha = linha + 1;
			row = dadosRelatorio.createRow(linha);

			criaColuna(lista.get(i).getNome(), cc, 0, row);
			criaColuna(lista.get(i).getQuantidade().toString(), cc, 1, row);
			criaColuna(Format.formatBigDecimal(lista.get(i).getValor()), cc, 2, row);
		}

		BigDecimal totalReceita = lista.stream().map(x -> x.getValor() == null ? BigDecimal.ZERO : x.getValor())
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		linha += 1;

		row = dadosRelatorio.createRow(linha);
		criaColuna("Total de receitas", cc, 1, row);
		criaColuna(Format.formatBigDecimal(totalReceita), cc, 2, row);

		autoSizeColuna(dadosRelatorio, 3);

		return gerarExcel(relatorioXls, "Relatório de saldo todos os cliente.xls");
	}

	public void salvar(MovimentacaoModel movimentacaoModel) {
		this.movimentacaoRepository.save(movimentacaoModel);
	}

	public Long consultaQuantidadeMovimentacao() {
		return this.movimentacaoRepository.count();
	}

	public BigDecimal consultaTotalMovimentacao() {
		return this.movimentacaoRepository.consultaTotalMovimento();
	}

	public BigDecimal consultaTotalMovimentacaoMensal(int mes, int ano) {
		return this.movimentacaoRepository.consultaTotalMovimentacaoMensal(mes, ano);
	}

	public BigDecimal consultaTotalMovimentacaoAno(int ano) {
		return this.movimentacaoRepository.consultaTotalMovimentacaoAno(ano);
	}

	public BigDecimal consultaTotalMovimentacaoSemana() {
		return this.movimentacaoRepository.consultaTotalMovimentacaoSemana();
	}

}
