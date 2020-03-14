package com.rafaelamorim.services;

import static java.util.Collections.emptyList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rafaelamorim.domain.Usuario;
import com.rafaelamorim.repositories.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = repository.findByUsername(username);
		
		if(usuario == null) {
			throw new UsernameNotFoundException(username);
		}
		
		return new User(usuario.getUsername(), usuario.getSenha(), emptyList());
	}

}
