package com.rafaelamorim.services;

import java.util.Date;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.rafaelamorim.domain.Recebimento;
import com.rafaelamorim.repositories.RecebimentoRepository;

@Service
public class RecebimentoService extends GenericCrudService<Recebimento, Integer, RecebimentoRepository>{
	
	public Set<Recebimento> findDataBetween(Date inicio, Date fim) {
		return repository.findByDataBetween(inicio, fim);
	}
	
}
