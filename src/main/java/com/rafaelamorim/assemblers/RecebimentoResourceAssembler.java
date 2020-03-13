package com.rafaelamorim.assemblers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Controller;

import com.rafaelamorim.domain.Recebimento;
import com.rafaelamorim.resources.RecebimentoResource;

@Controller
public class RecebimentoResourceAssembler implements ResourceAssembler<Recebimento, Resource<Recebimento>> {
	
	@Override
	public Resource<Recebimento> toResource(Recebimento entity) {
		Resource<Recebimento> resource = null;
		resource = new Resource<Recebimento>(entity, 
				linkTo(methodOn(RecebimentoResource.class).find(entity.getId())).withSelfRel(),
				linkTo(methodOn(RecebimentoResource.class).findAll()).withRel("recebimentos"));
		return resource;
	}

}
