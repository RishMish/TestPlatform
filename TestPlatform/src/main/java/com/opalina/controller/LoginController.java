package com.opalina.controller;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.opalina.model.Response;
import com.opalina.service.LoginService;

@Component
@Validated
@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
public class LoginController {
	
	@Autowired
	LoginService loginservice;
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response login(@FormParam("email") @NotNull(message="is required") @Email(message="Please provide a valid email address") @Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address") String email,
	@FormParam("password") @NotNull(message="is required") String pass) {
		System.out.println(email+" "+pass);
		return loginservice.checkValidity(email, pass);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor=new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class,stringTrimmerEditor);
	}
}
	

