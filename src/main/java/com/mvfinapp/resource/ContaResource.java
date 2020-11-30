package com.mvfinapp.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvfinapp.dto.ContaDto;
import com.mvfinapp.dto.ContaPequisaDto;
import com.mvfinapp.dto.EnderecoDto;
import com.mvfinapp.dto.MessagemDto;
import com.mvfinapp.service.ContaService;
import com.mvfinapp.vo.ContaVo;
import com.mvfinapp.vo.ResultVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/conta")
@Api(tags = { "Conta" })
public class ContaResource extends BaseResource {

	@Autowired
	private ContaService contaService;

	@ApiResponse(code = 200, message = "O serviço retorna a lista de contas dos clientes.")
	@ApiOperation(value = "Consulta de contas.", response = EnderecoDto.class, notes = "Serviço de consulta de contas com as seus clientes. Retorna todos registros ou pode ser feito a pesquisa usando os filtros.")
	@PostMapping(value = "/consulta-conta")
	public ResponseEntity<Object> consultaEndereco(
			@RequestBody @ApiParam(value = "Dto com campos de filtros da pesquisa de conta.", required = true) ContaPequisaDto dto,
			HttpServletRequest req) {
		try {
			log.info("ContaResource::consultaConta::ContaPequisaDto: {}", dto.toString());
			ResultVo<ContaVo> lista = this.contaService.consultaConta(dto);
			log.info("ContaResource::consultaConta::ContaVo: {}", lista.toString());
			return new ResponseEntity<>(lista, HttpStatus.OK);
		} catch (ValidationException e) {
			log.error("ContaResource::consultaConta::mensagem: {}", e.getMessage());
			return new ResponseEntity<>(new MessagemDto(e.getMessage()), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("ContaResource::consultaConta::Exception: {}", e.toString());
			return new ResponseEntity<>(super.logService.log(e, req), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiResponse(code = 200, message = "Serviço salva Conta.")
	@ApiOperation(value = "Salvar Conta.", notes = "Serviço para salvar a Conta com as seus clientes.")
	@PostMapping(value = "/salvar-conta")
	public ResponseEntity<Object> salvaConta(
			@RequestBody @ApiParam(value = "Dto com campos de filtros da pesquisa de Contas.", required = true) List<ContaDto> dto,
			HttpServletRequest req) {
		try {
			log.info("ContaResource::salvaConta::ContaDto: {}", dto.toString());
			this.contaService.salvarConta(dto);
			log.info("ContaResource::salvaConta");
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (ValidationException e) {
			log.error("ContaResource::salvaConta::mensagem: {}", e.getMessage());
			return new ResponseEntity<>(new MessagemDto(e.getMessage()), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("EnderecoResource::salvaEndereco::Exception: {}", e.toString());
			return new ResponseEntity<>(super.logService.log(e, req), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiResponse(code = 200, message = "Serviço deleta a Conta.")
	@ApiOperation(value = "Deletar Conta.", notes = "Serviço para deletar a Conta.")
	@DeleteMapping(value = "/deletar-conta/{id}")
	public ResponseEntity<Object> deletarConta(@PathVariable Long id, HttpServletRequest req) {
		try {
			log.info("EnderecoResource::deletaEndereco::id: {}", id.toString());
			this.contaService.deletarConta(id);
			log.info("EnderecoResource::deletaEndereco");
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (ValidationException e) {
			log.error("EnderecoResource::deletaEndereco::mensagem: {}", e.getMessage());
			return new ResponseEntity<>(new MessagemDto(e.getMessage()), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("EnderecoResource::deletaEndereco::Exception: {}", e.toString());
			return new ResponseEntity<>(super.logService.log(e, req), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
