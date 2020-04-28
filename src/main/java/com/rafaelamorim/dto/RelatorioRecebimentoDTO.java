package com.rafaelamorim.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class RelatorioRecebimentoDTO {

	@JsonFormat(pattern="dd/MM/yyyy")
	private Date inicio;
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date fim;
	
}
