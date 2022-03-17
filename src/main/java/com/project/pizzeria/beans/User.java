package com.project.pizzeria.beans;

import java.beans.Transient;
import java.io.Serializable;
import java.sql.Timestamp;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.pizzeria.beans.generic.GenericBean;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends GenericBean {
	private Long id;
	private String firstname;
	private String lastname;
	private String email;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	private String status;
	private String role;
	private Timestamp created_at;
	private Timestamp updated_at;
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Timestamp getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
	public Timestamp getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "User [firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", password=" + password
				+ ", status=" + status + ", role=" + role + ", created_at=" + created_at + ", updated_at=" + updated_at
				+ "]";
	}
	
}
