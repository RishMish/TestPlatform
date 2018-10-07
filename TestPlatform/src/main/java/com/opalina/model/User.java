package com.opalina.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.Pattern;


@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@NotBlank(message="is required")
	//@Pattern(regexp="^[a-zA-Z]+",message="Please provide a valid first name")
	private String firstName;
	@NotBlank(message="is required")
	//@Pattern(regexp="[a-zA-Z]+",message="Please provide a valid last name")
	private String lastName;
	@NotBlank(message="is required")
	private String password;
	@NotBlank(message="is required")
	@Column(unique=true)
	private String email;
	@NotBlank(message="is required")
	//@Pattern(regexp="^[0-9]{10}",message="Please provide a valid number")
	@Column(unique=true)
	private String contact;
	@ManyToOne
	private Role role;
	
	//Needed by JPA
	@SuppressWarnings("unused")
	private User(){	
	}
	public User(String firstName,String lastName,String email,String password,String contact) {
		this.firstName=firstName;
		this.lastName=lastName;
		this.password=password;
		this.email=email;
		this.contact=contact;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
}
