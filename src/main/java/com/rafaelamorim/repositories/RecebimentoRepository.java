package com.rafaelamorim.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rafaelamorim.domain.Recebimento;

@Repository
public interface RecebimentoRepository extends JpaRepository<Recebimento, Integer> {

}