package com.rafaelamorim.dto;

import java.util.Set;

import org.springframework.data.annotation.Transient;

import lombok.Data;

@Data
public class ClienteResponse {

	private Integer id;
	private String nome;
	private String cpf;
	private String contato;
	private Set<VendaResponse> vendas;
	private Set<RecebimentoResponse> recebimentos;
	@Transient
	private Float saldo;
	
	public Float getSaldo() {
		Float sumVendas = this.vendas.stream()
				.map(v -> v.getValor())
				.reduce((a, b) -> a + b)
				.get();
		Float sumRecebimentos = this.recebimentos.stream()
				.map(r -> r.getValor())
				.reduce((a, b) -> a + b)
				.get();
		return sumRecebimentos - sumVendas;
	}
	
}
