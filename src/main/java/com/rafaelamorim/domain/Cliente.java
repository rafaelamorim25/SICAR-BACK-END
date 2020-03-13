package com.rafaelamorim.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Table(name = "cli_cliente")
@Data
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cli_id")
	private Integer id;

	@Column(name = "cli_nome")
	private String nome;

	@Column(name = "cli_cpf", unique = true)
	private String cpf;

	@Column(name = "cli_contato")
	private String contato;

	@OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
	private Set<Venda> vendas;

	@JsonBackReference
	@OneToMany(mappedBy = "cliente")
	private Set<Recebimento> recebimentos;

}
