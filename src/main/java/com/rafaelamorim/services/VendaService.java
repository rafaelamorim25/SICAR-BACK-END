package com.rafaelamorim.services;

import org.springframework.stereotype.Service;

import com.rafaelamorim.domain.Venda;
import com.rafaelamorim.repositories.VendaRepository;

@Service
public class VendaService extends GenericCrudService<Venda, Integer, VendaRepository> {

}
