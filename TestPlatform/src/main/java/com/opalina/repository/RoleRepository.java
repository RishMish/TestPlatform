package com.opalina.repository;

import org.springframework.data.repository.CrudRepository;

import com.opalina.model.Role;

public interface RoleRepository extends CrudRepository<Role,Integer> {
	Role findByRole(String role);
}
