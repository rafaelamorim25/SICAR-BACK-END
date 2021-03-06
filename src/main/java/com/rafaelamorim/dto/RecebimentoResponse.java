package com.rafaelamorim.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class RecebimentoResponse {

	private Integer id;
	private Float valor;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date data;
	
}
