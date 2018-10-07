package com.opalina.service;

import com.opalina.model.User;

public interface UserService {
	public User findByEmail(String email);
}
