package com.opalina.model;

public class LoginResponse extends Response {
	private String role;
	
	public LoginResponse() {
		
	}
	
	public LoginResponse(String message,String status, String role) {
		this.role=role;
		super.setStatus(status);
		super.setMessage(message);
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
