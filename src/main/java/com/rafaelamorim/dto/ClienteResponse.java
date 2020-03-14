package com.rafaelamorim.dto;

import java.util.Set;

import lombok.Data;

@Data
public class ClienteResponse {

	private Integer id;
	private String nome;
	private String cpf;
	private String contato;
	private Set<VendaResponse> vendas;
	private Set<RecebimentoResponse> recebimentos;
	
}
