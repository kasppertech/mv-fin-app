package com.mvfinapp.resource;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvfinapp.dto.MessagemDto;
import com.mvfinapp.dto.RelatorioReceitaEmpresaDto;
import com.mvfinapp.dto.RelatorioSaldoClientePeriodoDto;
import com.mvfinapp.service.MovimentacaoService;
import com.mvfinapp.vo.ArquivoXlsVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/movimentacao")
@Api(tags = { "Movimentação" })
public class MovimentacaoResource extends BaseResource {

	@Autowired
	private MovimentacaoService movimentacaoService;

	@ApiResponse(code = 200, message = "O serviço retorna o relatorio de saldo por cliente com sucesso.")
	@ApiOperation(value = "Relatório de saldo de clientes.", notes = "Serviço retorna o relatório de saldo de por cliente.")
	@GetMapping(value = "/relatorio-saldo-cliente/{idCliente}")
	public ResponseEntity<Object> relatorioSaldoCliente(
			@PathVariable @ApiParam(value = "Código do cliente.", required = true) long idCliente,
			HttpServletRequest req) {

		try {

			log.info("MovimentacaoResource::relatorioSaldoCliente::idCliente: {}", idCliente);

			ArquivoXlsVo arquivo = this.movimentacaoService.consultaSaldoCliente(idCliente);

			log.info("MovimentacaoResource::relatorioSaldoCliente");

			return new ResponseEntity<Object>(arquivo, HttpStatus.OK);
		} catch (ValidationException e) {

			log.error("MovimentacaoResource::relatorioSaldoCliente::mensagem: {}", e.getMessage());
			return new ResponseEntity<Object>(new MessagemDto(e.getMessage()), HttpStatus.CONFLICT);

		} catch (Exception e) {

			log.error("MovimentacaoResource::relatorioSaldoCliente::Exception: {}", e.toString());
			return new ResponseEntity<Object>(super.logService.log(e, req), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiResponse(code = 200, message = "O serviço retorna o relatorio de saldo por cliente e período com sucesso.")
	@ApiOperation(value = "Relatório de saldo de clientes e período.", notes = "Serviço retorna o relatório de saldo de por cliente e período.")
	@PostMapping(value = "/relatorio-saldo-cliente-periodo")
	public ResponseEntity<Object> relatorioSaldoClientePeriodo(
			@RequestBody @ApiParam(value = "Dto com campos de filtros da pesquisa do relatório.", required = true) RelatorioSaldoClientePeriodoDto dto,
			HttpServletRequest req) {

		try {

			log.info("MovimentacaoResource::relatorioSaldoClientePeriodo::RelatorioSaldoClientePeriodoDto: {}",
					dto.toString());

			ArquivoXlsVo arquivo = this.movimentacaoService.consultaSaldoClientePorPeriodo(dto);

			log.info("MovimentacaoResource::relatorioSaldoClientePeriodo");

			return new ResponseEntity<Object>(arquivo, HttpStatus.OK);
		} catch (ValidationException e) {

			log.error("MovimentacaoResource::relatorioSaldoClientePeriodo::mensagem: {}", e.getMessage());
			return new ResponseEntity<Object>(new MessagemDto(e.getMessage()), HttpStatus.CONFLICT);

		} catch (Exception e) {

			log.error("MovimentacaoResource::relatorioSaldoClientePeriodo::Exception: {}", e.toString());
			return new ResponseEntity<Object>(super.logService.log(e, req), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiResponse(code = 200, message = "O serviço retorna o relatorio com o saldo de todos os cliente com sucesso.")
	@ApiOperation(value = "Relatório de saldo de todos os clientes.", notes = "Serviço retorna o relatório de saldo de todos os cliente.")
	@GetMapping(value = "/relatorio-saldo-todos-cliente")
	public ResponseEntity<Object> relatorioSaldoTodosCliente(HttpServletRequest req) {

		try {

			log.info("MovimentacaoResource::relatorioSaldoTodosCliente");

			ArquivoXlsVo arquivo = this.movimentacaoService.consultaSaldoTodosCliente();

			log.info("MovimentacaoResource::relatorioSaldoTodosCliente");

			return new ResponseEntity<Object>(arquivo, HttpStatus.OK);
		} catch (ValidationException e) {

			log.error("MovimentacaoResource::relatorioSaldoTodosCliente::mensagem: {}", e.getMessage());
			return new ResponseEntity<Object>(new MessagemDto(e.getMessage()), HttpStatus.CONFLICT);

		} catch (Exception e) {

			log.error("MovimentacaoResource::relatorioSaldoTodosCliente::Exception: {}", e.toString());
			return new ResponseEntity<Object>(super.logService.log(e, req), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiResponse(code = 200, message = "O serviço retorna o relatório de receita das empresas por período com sucesso.")
	@ApiOperation(value = "Relatório de receita das empresas.", notes = "Serviço retorna o relatório de receita das empresas por período.")
	@PostMapping(value = "/relatorio-receita-empresas-periodo")
	public ResponseEntity<Object> relatorioReceitaEmpresasPeriodo(
			@RequestBody @ApiParam(value = "Dto com campos de filtros da pesquisa do relatório.", required = true) RelatorioReceitaEmpresaDto dto,
			HttpServletRequest req) {

		try {

			log.info("MovimentacaoResource::relatorioReceitaEmpresasPeriodo");

			ArquivoXlsVo arquivo = this.movimentacaoService.consultaRelatorioReceitaEmpresasPeriodo(dto);

			log.info("MovimentacaoResource::relatorioReceitaEmpresasPeriodo");

			return new ResponseEntity<Object>(arquivo, HttpStatus.OK);
		} catch (ValidationException e) {

			log.error("MovimentacaoResource::relatorioReceitaEmpresasPeriodo::mensagem: {}", e.getMessage());
			return new ResponseEntity<Object>(new MessagemDto(e.getMessage()), HttpStatus.CONFLICT);

		} catch (Exception e) {

			log.error("MovimentacaoResource::relatorioReceitaEmpresasPeriodo::Exception: {}", e.toString());
			return new ResponseEntity<Object>(super.logService.log(e, req), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
