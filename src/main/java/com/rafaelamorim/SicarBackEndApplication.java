package com.rafaelamorim;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rafaelamorim.domain.Cliente;
import com.rafaelamorim.domain.Venda;
import com.rafaelamorim.services.ClienteService;
import com.rafaelamorim.services.RecebimentoService;
import com.rafaelamorim.services.VendaService;

@SpringBootApplication
public class SicarBackEndApplication implements CommandLineRunner {
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	VendaService vendaService;
	
	@Autowired
	RecebimentoService recebimentoService;
	

	public static void main(String[] args) {
		SpringApplication.run(SicarBackEndApplication.class, args);
	}

	
	@Override
	public void run(String... args) throws Exception {
		
		/*
		
		Cliente c1 = new Cliente(null, "Rafael amorim", "12345678999", "61991955333");
		
		Venda v1 = new Venda(c1, null, 91F, new Date());
		
		clienteService.insert(c1);
		vendaService.insert(v1); */
	}
	
}
