package com.mvfinapp.resource;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvfinapp.dto.ClienteDto;
import com.mvfinapp.dto.ClientePequisaDto;
import com.mvfinapp.dto.MessagemDto;
import com.mvfinapp.service.ClienteService;
import com.mvfinapp.vo.ClienteCodigoVo;
import com.mvfinapp.vo.ClienteVo;
import com.mvfinapp.vo.ResultVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/cliente")
@Api(tags = { "Cliente" })
public class ClienteResource extends BaseResource {

	@Autowired
	private ClienteService clienteService;

	@ApiResponse(code = 200, message = "O serviço retorna a lista de clientes com sucesso.")
	@ApiOperation(value = "Consulta de clientes.", notes = "Serviço de consulta de clientes, retorna todos registros ou pode ser feito a pesquisa usando os filtros.")
	@PostMapping(value = "/consulta-cliente")
	public ResponseEntity<Object> consultaCliente(@RequestBody ClientePequisaDto dto, HttpServletRequest req) {
		try {

			log.info("ClienteResource::consultaCliente::ClientePequisaDto: {}", dto.toString());

			ResultVo<ClienteVo> lista = this.clienteService.consultaCliente(dto);

			log.info("ClienteResource::consultaCliente::ResultVo: {}", lista.toString());

			return new ResponseEntity<>(lista, HttpStatus.OK);
		} catch (ValidationException e) {

			log.error("ClienteResource::consultaCliente::mensagem: {}", e.getMessage());
			return new ResponseEntity<>(new MessagemDto(e.getMessage()), HttpStatus.CONFLICT);

		} catch (Exception e) {

			log.error("ClienteResource::consultaCliente::Exception: {}", e.toString());
			return new ResponseEntity<>(super.logService.log(e, req), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiResponse(code = 200, message = "o serviço retorna um cliente com sucesso.")
	@ApiOperation(value = "Consulta de cliente por código,", notes = "Serviço de consulta de cliente, a pesquisa é feita pelo o código do cliente.")
	@GetMapping(value = "/consulta-cliente-por-codigo/{idCliente}")
	public ResponseEntity<Object> consultaClientePorId(
			@PathVariable @ApiParam(value = "Código do cliente.", required = true) long idCliente,
			HttpServletRequest req) {
		try {

			log.info("ClienteResource::consultaClientePorId::idCliente: {}", idCliente);

			ClienteVo cliente = this.clienteService.consultaClientePorCodigo(idCliente);

			log.info("ClienteResource::consultaClientePorId::ClienteVo: {}", cliente.toString());

			return new ResponseEntity<>(cliente, HttpStatus.OK);
		} catch (ValidationException e) {

			log.error("ClienteResource::consultaClientePorId::mensagem: {}", e.getMessage());
			return new ResponseEntity<>(new MessagemDto(e.getMessage()), HttpStatus.CONFLICT);

		} catch (Exception e) {

			log.error("ClienteResource::consultaClientePorId::Exception: {}", e.toString());
			return new ResponseEntity<>(super.logService.log(e, req), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiResponse(code = 200, message = "Serviço salva cliente, contas e endereço com sucesso.")
	@ApiOperation(value = "Salvar cliente.", notes = "Serviço para salvar o cliente.")
	@PostMapping(value = "/salvar-cliente")
	public ResponseEntity<Object> salvaCliente(
			@RequestBody @ApiParam(value = "Dto com campos de filtros da pesquisa de clientes.", required = true) ClienteDto dto,
			HttpServletRequest req) {
		try {

			log.info("ClienteResource::salvaCliente::ClienteDto: {}", dto.toString());

			ClienteCodigoVo cliente = this.clienteService.salvarCliente(dto);

			log.info("ClienteResource::salvaCliente");

			return new ResponseEntity<>(cliente, HttpStatus.OK);
		} catch (ValidationException e) {

			log.error("ClienteResource::salvaCliente::mensagem: {}", e.getMessage());
			return new ResponseEntity<>(new MessagemDto(e.getMessage()), HttpStatus.CONFLICT);

		} catch (Exception e) {

			log.error("ClienteResource::salvaCliente::Exception: {}", e.toString());
			return new ResponseEntity<>(super.logService.log(e, req), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiResponse(code = 200, message = "o serviço exclusão de cliente com sucesso.")
	@ApiOperation(value = "Exclusão de cliente por código,", notes = "Serviço de exclusão de cliente, a pesquisa é feita pelo o código do cliente.")
	@DeleteMapping(value = "/exclusao-cliente-por-codigo/{idCliente}")
	public ResponseEntity<Object> exclusaoClientePorId(
			@PathVariable @ApiParam(value = "Código do cliente.", required = true) long idCliente,
			HttpServletRequest req) {
		try {

			log.info("ClienteResource::consultaClientePorId::idCliente: {}", idCliente);

			this.clienteService.exclusaoClientePorCodigo(idCliente);

			log.info("ClienteResource::consultaClientePorId");

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (ValidationException e) {

			log.error("ClienteResource::consultaClientePorId::mensagem: {}", e.getMessage());
			return new ResponseEntity<>(new MessagemDto(e.getMessage()), HttpStatus.CONFLICT);

		} catch (Exception e) {

			log.error("ClienteResource::consultaClientePorId::Exception: {}", e.toString());
			return new ResponseEntity<>(super.logService.log(e, req), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
