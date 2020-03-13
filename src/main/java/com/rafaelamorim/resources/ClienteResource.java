package com.rafaelamorim.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.hibernate.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rafaelamorim.assemblers.ClienteResourceAssembler;
import com.rafaelamorim.domain.Cliente;
import com.rafaelamorim.dto.ClienteDTO;
import com.rafaelamorim.services.ClienteService;

@RestController
@CrossOrigin
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	ClienteService service;
	
	@Autowired
	ClienteResourceAssembler assembler;
	
	@Autowired
	private ModelMapper mapper;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Resource<Cliente> find(@PathVariable Integer id) {
		Cliente cliente = service.find(id).orElseThrow(() -> new ObjectNotFoundException(Cliente.class,
				"Cliente não encontrado! Id: " + id));
		
		return assembler.toResource(cliente);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public Resources<Resource<Cliente>> findAll() {
		List<Resource<Cliente>> list = StreamSupport.stream(service.findAll().spliterator(), false)
				.map(assembler::toResource)
				.collect(Collectors.toList());
		return new Resources<>(list, linkTo(methodOn(ClienteResource.class).findAll()).withSelfRel());		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteDTO obj) {
		Cliente cliente = mapper.map(obj, Cliente.class);
		cliente = service.insert(cliente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO obj, @PathVariable Integer id) {
		Cliente cliente = mapper.map(obj,  Cliente.class);
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
