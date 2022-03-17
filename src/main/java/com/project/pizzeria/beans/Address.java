package com.project.pizzeria.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.pizzeria.beans.generic.GenericBean;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Address extends GenericBean {
	private Long id;
	private String address1;
	private String address2;
	private String city;
	private String zipcode;
	private String country;
	private Long user;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Long getUser() {
		return user;
	}
	public void setUser(Long user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Address [id=" + id + ", address1=" + address1 + ", address2=" + address2 + ", city=" + city
				+ ", zipcode=" + zipcode + ", country=" + country + ", user=" + user + "]";
	}

	
}
