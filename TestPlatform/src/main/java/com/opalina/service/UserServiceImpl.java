package com.opalina.service;

/*
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.opalina.model.User;
import com.opalina.repository.UserRepository;
*/

public class UserServiceImpl /*implements UserService,UserDetailsService*/{
	/*
	@Autowired
	UserRepository userrepository;
	
	@Override
	public User findByEmail(String email) {
		return userrepository.findByEmail(email);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=findByEmail(username);
		if(user==null) {
			throw new UsernameNotFoundException(username);
		}
		return new UserDetailsImpl(user);
	}
	*/
}
