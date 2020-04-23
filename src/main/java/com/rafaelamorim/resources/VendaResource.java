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
import com.rafaelamorim.domain.FormaPagamento;
import com.rafaelamorim.domain.Venda;
import com.rafaelamorim.dto.VendaDTO;
import com.rafaelamorim.services.VendaService;

@RestController
@CrossOrigin
@RequestMapping(value="/vendas")
public class VendaResource {
	
	@Autowired
	VendaService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Venda> find(@PathVariable Integer id) {
		Venda obj =  service.find(id).orElseThrow(() -> new ObjectNotFoundException(Cliente.class,
				"Venda não encontrada! Id: " + id));
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Venda>> findAll() {
		List<Venda> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Venda> insert(@Valid @RequestBody VendaDTO obj) throws URISyntaxException {
		Venda venda = new Venda();
		PropertyMap<VendaDTO, Venda> vendaMap = new PropertyMap<VendaDTO, Venda>() {
			@Override
			protected void configure() {
				map().setId(null);
			}
		};
		ModelMapper mapper = new ModelMapper();
		mapper.addMappings(vendaMap);
		mapper.map(obj, venda);
		venda = service.insert(venda);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(venda.getId()).toUri();
		return ResponseEntity.created(uri).body(venda);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Venda> update(@Valid @RequestBody VendaDTO obj, @PathVariable Integer id) throws URISyntaxException {
		Venda venda = service.find(id).map(v -> {
			PropertyMap<VendaDTO, Venda> vendaMap = new PropertyMap<VendaDTO, Venda>() {
				@Override
				protected void configure() {
					map().setId(id);
					skip(destination.getCliente());
					skip(destination.getFormaPagamento());
				}
			};
			ModelMapper mapper = new ModelMapper();
			mapper.addMappings(vendaMap);
			mapper.map(obj, v);
			return v;
		}).orElseThrow(() -> new ObjectNotFoundException(Cliente.class,
				"Venda não encontrada! Id: " + id));
		venda.setCliente(Cliente.builder().id(obj.getClienteId()).build());
		venda.setFormaPagamento(FormaPagamento.builder().id(obj.getFormaPagamentoId()).build());
		service.update(venda);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
