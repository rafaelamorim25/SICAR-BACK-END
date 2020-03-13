package com.rafaelamorim.resources;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.hibernate.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.rafaelamorim.assemblers.RecebimentoResourceAssembler;
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
	

	@Autowired
	RecebimentoResourceAssembler assembler;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Resource<Recebimento> find(@PathVariable Integer id) {
		Recebimento obj =  service.find(id).orElseThrow(() -> new ObjectNotFoundException(Cliente.class,
				"Recebimento não encontrado! Id: " + id));
		return assembler.toResource(obj);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public Resources<Resource<Recebimento>> findAll() {
		List<Resource<Recebimento>> list = service.findAll().stream().map(assembler::toResource).collect(Collectors.toList());
		return new Resources<>(list, linkTo(methodOn(RecebimentoResource.class).findAll()).withSelfRel());
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Resource<Recebimento>> insert(@Valid @RequestBody RecebimentoDTO obj) throws URISyntaxException {
		Recebimento recebimento = new Recebimento();
		PropertyMap<RecebimentoDTO, Recebimento> recebimentoMap = new PropertyMap<RecebimentoDTO, Recebimento>() {
			@Override
			protected void configure() {
				map().setId(null);
				map().getCliente().setId(source.getClienteId());
			}
		};
		ModelMapper mapper = new ModelMapper();
		mapper.addMappings(recebimentoMap);
		mapper.map(obj, recebimento);
		Resource<Recebimento> resource = assembler.toResource(service.insert(recebimento));
		return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Resource<Recebimento>> update(@Valid @RequestBody RecebimentoDTO obj, @PathVariable Integer id) throws URISyntaxException {
		Recebimento recebimentoAlterado = service.find(id).map(recebimento -> {
			PropertyMap<RecebimentoDTO, Recebimento> recebimentoMap = new PropertyMap<RecebimentoDTO, Recebimento>() {
				@Override
				protected void configure() {
					map().setId(id);
					map().getCliente().setId(source.getClienteId());
				}
			};
			ModelMapper mapper = new ModelMapper();
			mapper.addMappings(recebimentoMap);
			mapper.map(obj, recebimento);
			return service.update(recebimento);
		}).orElseThrow(() -> new ObjectNotFoundException(Cliente.class,
				"Recebimento não encontrado! Id: " + id));
		Resource<Recebimento> resource = assembler.toResource(recebimentoAlterado);
		return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
