package com.rafaelamorim.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rafaelamorim.domain.FormaPagamento;

import lombok.Data;

@Data
public class VendaDTO {

	private Integer id;
	private Float valor;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date data;
	private Integer clienteId;
	
	private FormaPagamento formaPagamento;

}
