package com.rafaelamorim.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rafaelamorim.domain.Recebimento;

@Repository
public interface RecebimentoRepository extends JpaRepository<Recebimento, Integer> {
	
	Set<Recebimento> findByClienteId(Integer clienteId);

}
