package com.rafaelamorim.services;

import org.springframework.stereotype.Service;

import com.rafaelamorim.domain.Cliente;
import com.rafaelamorim.repositories.ClienteRepository;

@Service
public class ClienteService extends GenericCrudService<Cliente, Integer, ClienteRepository>{

}
