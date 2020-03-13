package com.rafaelamorim.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rafaelamorim.domain.Venda;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Integer> {
	Set<Venda> findByClienteId(int clienteId);
}
