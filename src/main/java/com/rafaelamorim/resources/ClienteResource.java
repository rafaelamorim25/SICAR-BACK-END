package com.rafaelamorim.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.hibernate.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rafaelamorim.domain.Cliente;
import com.rafaelamorim.dto.ClienteDTO;
import com.rafaelamorim.dto.ClienteResponse;
import com.rafaelamorim.services.ClienteService;
import com.rafaelamorim.services.VendaService;

@RestController
@CrossOrigin
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	ClienteService service;

	@Autowired
	VendaService vendaService;

	@Autowired
	private ModelMapper mapper;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ClienteResponse> find(@PathVariable Integer id) {
		ClienteResponse clienteResponse = service.find(id).map(cliente -> mapper.map(cliente, ClienteResponse.class))
				.orElseThrow(() -> new ObjectNotFoundException(Cliente.class, "Cliente não encontrado! Id: " + id));
		return ResponseEntity.ok().body(clienteResponse);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteResponse>> findAll() {
		List<ClienteResponse> list = service.findAll().stream().map(cliente -> {	
			ClienteResponse clienteResponse =  mapper.map(cliente, ClienteResponse.class);
			return clienteResponse;
		}).collect(Collectors.toList());
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteDTO obj) {
		Cliente cliente = mapper.map(obj, Cliente.class);
		cliente = service.insert(cliente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO obj, @PathVariable Integer id) {
		Cliente cliente = mapper.map(obj, Cliente.class);
		cliente.setId(id);
		cliente = service.update(cliente);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
