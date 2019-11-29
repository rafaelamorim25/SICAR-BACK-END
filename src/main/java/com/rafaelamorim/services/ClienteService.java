package com.rafaelamorim.services;

import org.springframework.stereotype.Service;

import com.rafaelamorim.domain.Cliente;
import com.rafaelamorim.repositories.ClienteRepository;
import com.rafaelamorim.services.exception.DataIntegrityException;
import com.rafaelamorim.services.utils.ValidaCPF;

@Service
public class ClienteService extends GenericCrudService<Cliente, Integer, ClienteRepository>{

	@Override
	public Cliente insert(Cliente obj) {
		if(ValidaCPF.isCPF(obj.getCpf())) {
			return super.insert(obj);
		}
		throw new DataIntegrityException("CPF inválido");
	}

	@Override
	public Cliente update(Cliente obj) {
		if(ValidaCPF.isCPF(obj.getCpf())) {
			return super.update(obj);
		}
		throw new DataIntegrityException("CPF inválido");
	}
}
