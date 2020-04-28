package com.rafaelamorim.resources;
import java.net.URISyntaxException;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rafaelamorim.domain.Recebimento;
import com.rafaelamorim.dto.RelatorioRecebimentoDTO;
import com.rafaelamorim.dto.RelatorioRecebimentoResponse;
import com.rafaelamorim.services.RecebimentoService;

@RestController
@CrossOrigin
@RequestMapping(value="/relatorios")
public class RelatorioResource {
	
	@Autowired
	RecebimentoService service;
	
	@RequestMapping(value = "/recebimentos", method = RequestMethod.GET)
	public ResponseEntity<RelatorioRecebimentoResponse> update(@Valid @RequestBody RelatorioRecebimentoDTO obj) throws URISyntaxException {
		Set<Recebimento> list = service.findDataBetween(obj.getInicio(), obj.getFim());
		double total = list.stream().mapToDouble(rec -> rec.getValor().doubleValue()).sum();
		RelatorioRecebimentoResponse response = new RelatorioRecebimentoResponse();
		response.setRecebimentos(list);
		response.setTotal(total);
		return ResponseEntity.ok().body(response);
	}
}
