package com.lacosdaalegria.intralacos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lacosdaalegria.intralacos.model.Role;
import com.lacosdaalegria.intralacos.repository.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository repository;
	
	public Role getRole(String role) {
		return repository.findByRole(role);
	}
	
}
