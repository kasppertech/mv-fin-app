package com.mvfinapp.resource;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvfinapp.dto.MessagemDto;
import com.mvfinapp.service.DashboardService;
import com.mvfinapp.vo.DashboardMovimentoVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/dashboard")
@Api(tags = { "Dashboard" })
public class DashboardResource extends BaseResource {

	@Autowired
	private DashboardService dashboardService;

	@ApiResponse(code = 200, message = "O serviço retorna o detalhe das movimentações;.")
	@ApiOperation(value = "Dashboard com detalhe das movimentações.", notes = "Serviço retorna o detalhe das movimentações.")
	@GetMapping(value = "/movimentacao")
	public ResponseEntity<Object> dashboardMovimento(HttpServletRequest req) {

		try {

			log.info("DashboardResource::dashboardMovimento");

			DashboardMovimentoVo vo = this.dashboardService.dashboardMovimento();

			log.info("DashboardResource::dashboardMovimento::DashboardMovimentoVo: {}", vo);

			return new ResponseEntity<Object>(vo, HttpStatus.OK);
		} catch (ValidationException e) {

			log.error("DashboardResource::dashboardMovimento::mensagem: {}", e.getMessage());
			return new ResponseEntity<Object>(new MessagemDto(e.getMessage()), HttpStatus.CONFLICT);

		} catch (Exception e) {

			log.error("DashboardResource::dashboardMovimento::Exception: {}", e.toString());
			return new ResponseEntity<Object>(super.logService.log(e, req), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
