package com.lacosdaalegria.intralacos.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

	Role findByRole(String role);
}
