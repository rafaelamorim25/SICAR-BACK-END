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
import com.rafaelamorim.assemblers.VendaResourceAssembler;
import com.rafaelamorim.domain.Cliente;
import com.rafaelamorim.domain.Venda;
import com.rafaelamorim.dto.VendaDTO;
import com.rafaelamorim.services.VendaService;

@RestController
@CrossOrigin
@RequestMapping(value="/vendas")
public class VendaResource {
	
	@Autowired
	VendaService service;
	
	@Autowired
	VendaResourceAssembler assembler;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Resource<Venda> find(@PathVariable Integer id) {
		Venda obj =  service.find(id).orElseThrow(() -> new ObjectNotFoundException(Cliente.class,
				"Venda não encontrada! Id: " + id));
		return assembler.toResource(obj);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public Resources<Resource<Venda>> findAll() {
		List<Resource<Venda>> list = service.findAll().stream().map(assembler::toResource).collect(Collectors.toList());
		return new Resources<>(list, linkTo(methodOn(VendaResource.class).findAll()).withSelfRel());
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Resource<Venda>> insert(@Valid @RequestBody VendaDTO obj) throws URISyntaxException {
		Venda venda = new Venda();
		PropertyMap<VendaDTO, Venda> vendaMap = new PropertyMap<VendaDTO, Venda>() {
			@Override
			protected void configure() {
				map().setId(null);
				map().getCliente().setId(source.getClienteId());
			}
		};
		ModelMapper mapper = new ModelMapper();
		mapper.addMappings(vendaMap);
		mapper.map(obj, venda);
		Resource<Venda> resource = assembler.toResource(service.insert(venda));
		return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Resource<Venda>> update(@Valid @RequestBody VendaDTO obj, @PathVariable Integer id) throws URISyntaxException {
		Venda vendaAlterado = service.find(id).map(venda -> {
			PropertyMap<VendaDTO, Venda> vendaMap = new PropertyMap<VendaDTO, Venda>() {
				@Override
				protected void configure() {
					map().setId(id);
					map().getCliente().setId(source.getClienteId());
				}
			};
			ModelMapper mapper = new ModelMapper();
			mapper.addMappings(vendaMap);
			mapper.map(obj, venda);
			return service.update(venda);
		}).orElseThrow(() -> new ObjectNotFoundException(Cliente.class,
				"Venda não encontrada! Id: " + id));
		Resource<Venda> resource = assembler.toResource(vendaAlterado);
		return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
