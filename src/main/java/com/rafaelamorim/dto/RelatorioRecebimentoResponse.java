package com.rafaelamorim.dto;

import java.util.Set;

import com.rafaelamorim.domain.Recebimento;

import lombok.Data;

@Data
public class RelatorioRecebimentoResponse {
	
	Set<Recebimento> recebimentos;
	Double total;

}
