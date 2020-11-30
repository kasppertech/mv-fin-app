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

import com.mvfinapp.dto.EnderecoDto;
import com.mvfinapp.dto.EnderecoPequisaDto;
import com.mvfinapp.dto.MessagemDto;
import com.mvfinapp.service.EnderecoService;
import com.mvfinapp.vo.EnderecoVo;
import com.mvfinapp.vo.ResultVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/endereco")
@Api(tags = { "Endereco" })
public class EnderecoResource extends BaseResource {

	@Autowired
	private EnderecoService enderecoService;

	@ApiResponse(code = 200, message = "O serviço retorna a lista de endereço.")
	@ApiOperation(value = "Consulta de endereço.", response = EnderecoVo.class, notes = "Serviço de consulta de endereço com as seus clientes. Retorna todos registros ou pode ser feito a pesquisa usando os filtros.")
	@PostMapping(value = "/consulta-endereco")
	public ResponseEntity<Object> consultaEndereco(
			@RequestBody @ApiParam(value = "Dto com campos de filtros da pesquisa de endereço.", required = true) EnderecoPequisaDto dto,
			HttpServletRequest req) {
		try {
			log.info("EnredeçoResource::consultaEnredeço::EnredecoPequisaDto: {}", dto.toString());
			ResultVo<EnderecoVo> lista = this.enderecoService.consultaEndereco(dto);
			log.info("EnderecoResource::consultaEndereco::EnderecoVo: {}", lista.toString());
			return new ResponseEntity<>(lista, HttpStatus.OK);
		} catch (ValidationException e) {
			log.error("EnderecoResource::consultaEndereco::mensagem: {}", e.getMessage());
			return new ResponseEntity<>(new MessagemDto(e.getMessage()), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("EnderecoResource::consultaEndereco::Exception: {}", e.toString());
			return new ResponseEntity<>(super.logService.log(e, req), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiResponse(code = 200, message = "Serviço salva Endereco, contas e endereço com sucesso.")
	@ApiOperation(value = "Salvar Endereco.", notes = "Serviço para salvar o Endereco com as suas contas e endereço.")
	@PostMapping(value = "/salvar-endereco")
	public ResponseEntity<Object> salvaEndereco(
			@RequestBody @ApiParam(value = "Dto com campos de filtros da pesquisa de Enderecos.", required = true) List<EnderecoDto> dto,
			HttpServletRequest req) {
		try {
			log.info("EnderecoResource::salvaEndereco::EnderecoDto: {}", dto.toString());
			this.enderecoService.salvarEndereco(dto);
			log.info("EnderecoResource::salvaEndereco");
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (ValidationException e) {
			log.error("EnderecoResource::salvaEndereco::mensagem: {}", e.getMessage());
			return new ResponseEntity<>(new MessagemDto(e.getMessage()), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("EnderecoResource::salvaEndereco::Exception: {}", e.toString());
			return new ResponseEntity<>(super.logService.log(e, req), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiResponse(code = 200, message = "Serviço deleta o Endereco.")
	@ApiOperation(value = "Deletar Endereco.", notes = "Serviço para deletar o Endereco.")
	@DeleteMapping(value = "/deletar-endereco/{id}")
	public ResponseEntity<Object> deletarEndereco(@PathVariable Long id, HttpServletRequest req) {
		try {
			log.info("EnderecoResource::deletaEndereco::id: {}", id.toString());
			this.enderecoService.deletarEndereco(id);
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
