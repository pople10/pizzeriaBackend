package com.project.pizzeria.beans;

import com.project.pizzeria.beans.generic.GenericBean;

public class Log extends GenericBean {
	private Long id;
	private String ip;
	private Long user;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Long getUser() {
		return user;
	}
	public void setUser(Long user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Log [id=" + id + ", ip=" + ip + ", user=" + user + "]";
	}
	
}
