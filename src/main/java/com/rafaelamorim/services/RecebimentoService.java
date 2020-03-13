package com.rafaelamorim.services;

import org.springframework.stereotype.Service;

import com.rafaelamorim.domain.Recebimento;
import com.rafaelamorim.repositories.RecebimentoRepository;

@Service
public class RecebimentoService extends GenericCrudService<Recebimento, Integer, RecebimentoRepository>{
}
