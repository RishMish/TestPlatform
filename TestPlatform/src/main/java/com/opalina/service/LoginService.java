package com.opalina.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opalina.model.LoginResponse;
import com.opalina.model.Response;
import com.opalina.model.User;
import com.opalina.repository.UserRepository;

@Service
public class LoginService {	
	@Autowired
	private UserRepository userrepository;

	public Response checkValidity(String email,String pass) {
		User user=userrepository.findByEmail(email);
		if(user==null) {
			Response response=new Response("Email ID has not been registered","0");
			return response;
		}
		else {
			if(!user.getPassword().equals(pass)) {
				Response response=new Response("Incorrect Password","0");
				return response;
			}
		}
		return new LoginResponse("Login Succesful","1",user.getRole().getRole());
	}
}
