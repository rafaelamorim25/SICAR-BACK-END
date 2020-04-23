package com.rafaelamorim.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fpg_forma_pagamento")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormaPagamento implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name = "fpg_id")
	private Integer id;
	
	@Column(name = "fpg_descricao")
	private String descricao;
	

}
