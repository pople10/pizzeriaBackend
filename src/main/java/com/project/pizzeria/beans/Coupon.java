package com.project.pizzeria.beans;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.pizzeria.beans.generic.GenericBean;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Coupon extends GenericBean {
	private Long id;
	private String code;
	private Float amount;
	private String type;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private Timestamp expiration;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Timestamp getExpiration() {
		return expiration;
	}
	public void setExpiration(Timestamp expiration) {
		this.expiration = expiration;
	}
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", code=" + code + ", amount=" + amount + ", type=" + type + ", expiration="
				+ expiration + "]";
	}
	
}
