package com.rafaelamorim.dto;

import java.util.Set;

import com.rafaelamorim.domain.Venda;

import lombok.Data;

@Data
public class ClienteResponse {

	private Integer id;
	private String nome;
	private String cpf;
	private String contato;
	private Set<Venda> vendas;
	
}
