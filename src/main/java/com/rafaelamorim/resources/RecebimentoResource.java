package com.rafaelamorim.resources;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rafaelamorim.domain.Cliente;
import com.rafaelamorim.domain.Recebimento;
import com.rafaelamorim.dto.RecebimentoDTO;
import com.rafaelamorim.services.RecebimentoService;

@RestController
@CrossOrigin
@RequestMapping(value="/recebimentos")
public class RecebimentoResource {
	
	@Autowired
	RecebimentoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Recebimento> find(@PathVariable Integer id) {
		Recebimento obj =  service.find(id).orElseThrow(() -> new ObjectNotFoundException(Cliente.class,
				"Recebimento não encontrado! Id: " + id));
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Recebimento>> findAll() {
		List<Recebimento> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Recebimento> insert(@Valid @RequestBody RecebimentoDTO obj) throws URISyntaxException {
		Recebimento recebimento = new Recebimento();
		PropertyMap<RecebimentoDTO, Recebimento> recebimentoMap = new PropertyMap<RecebimentoDTO, Recebimento>() {
			@Override
			protected void configure() {
				map().setId(null);
			}
		};
		ModelMapper mapper = new ModelMapper();
		mapper.addMappings(recebimentoMap);
		mapper.map(obj, recebimento);
		recebimento = service.insert(recebimento);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(recebimento.getId()).toUri();
		return ResponseEntity.created(uri).body(recebimento);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Recebimento> update(@Valid @RequestBody RecebimentoDTO obj, @PathVariable Integer id) throws URISyntaxException {
		Recebimento recebimento = service.find(id).map(r -> {
			PropertyMap<RecebimentoDTO, Recebimento> recebimentoMap = new PropertyMap<RecebimentoDTO, Recebimento>() {
				@Override
				protected void configure() {
					map().setId(id);
					skip(destination.getCliente());
				}
			};
			ModelMapper mapper = new ModelMapper();
			mapper.addMappings(recebimentoMap);
			mapper.map(obj, r);
			return r;
		}).orElseThrow(() -> new ObjectNotFoundException(Cliente.class,
				"Recebimento não encontrado! Id: " + id));
		recebimento.setCliente(Cliente.builder().id(obj.getClienteId()).build());
		service.update(recebimento);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
