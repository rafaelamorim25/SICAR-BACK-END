package com.rafaelamorim.dto;

import java.util.Date;

import lombok.Data;

@Data
public class RecebimentoResponse {

	private Integer id;
	private Float valor;
	private Date data;
	
}
