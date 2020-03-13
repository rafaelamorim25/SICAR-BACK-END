package com.rafaelamorim.assemblers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Controller;

import com.rafaelamorim.domain.Cliente;
import com.rafaelamorim.resources.ClienteResource;

@Controller
public class ClienteResourceAssembler implements ResourceAssembler<Cliente, Resource<Cliente>> {
	
	@Override
	public Resource<Cliente> toResource(Cliente entity) {
		Resource<Cliente> resource = null;
		resource = new Resource<Cliente>(entity, 
				linkTo(methodOn(ClienteResource.class).find(entity.getId())).withSelfRel(),
				linkTo(methodOn(ClienteResource.class).findAll()).withRel("clientes"));
		return resource;
	}

}
