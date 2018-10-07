package com.opalina.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.opalina.model.Role;
import com.opalina.model.User;

public interface UserRepository extends CrudRepository<User,Long> {
	User findByEmail(String email);
	List<User> findByRole(Role role);
	boolean existsById(Long id);
	void deleteById(Long id);
}
