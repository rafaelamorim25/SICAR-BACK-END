package com.rafaelamorim.services;

import java.util.Set;
import org.springframework.stereotype.Service;

import com.rafaelamorim.domain.Venda;
import com.rafaelamorim.repositories.VendaRepository;

@Service
public class VendaService extends GenericCrudService<Venda, Integer, VendaRepository>{
	
	public Set<Venda> findByClienteId(int clienteId) {
		return repository.findByClienteId(clienteId);
	}
}
