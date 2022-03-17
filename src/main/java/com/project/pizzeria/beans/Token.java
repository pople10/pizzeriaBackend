package com.project.pizzeria.beans;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.pizzeria.beans.generic.GenericBean;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Token extends GenericBean {
	private Long id;
	private String token;
	private Timestamp expiration;
	private Long user;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Timestamp getExpiration() {
		return expiration;
	}
	public void setExpiration(Timestamp expiration) {
		this.expiration = expiration;
	}
	public Long getUser() {
		return user;
	}
	public void setUser(Long user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Token [id=" + id + ", token=" + token + ", expiration=" + expiration + ", user=" + user + "]";
	}
	
}
