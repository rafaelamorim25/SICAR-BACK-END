package com.rafaelamorim.assemblers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Controller;

import com.rafaelamorim.domain.Venda;
import com.rafaelamorim.resources.VendaResource;

@Controller
public class VendaResourceAssembler implements ResourceAssembler<Venda, Resource<Venda>> {
	
	@Override
	public Resource<Venda> toResource(Venda entity) {
		Resource<Venda> resource = null;
		resource = new Resource<Venda>(entity, 
				linkTo(methodOn(VendaResource.class).find(entity.getId())).withSelfRel(),
				linkTo(methodOn(VendaResource.class).findAll()).withRel("vendas"));
		return resource;
	}

}
