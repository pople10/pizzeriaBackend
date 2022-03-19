package com.project.pizzeria.beans;

import java.sql.Timestamp;

import com.project.pizzeria.beans.generic.GenericBean;

public class Order extends GenericBean {
	private Long id;
	private String coupon;
	private Float total;
	private Float paid;
	private String type;
	private String status;
	private Long delivery;
	private Long address;
	private Long product;
	private Timestamp wanted_at;
	private Timestamp created_at;
	private Timestamp updated_at;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCoupon() {
		return coupon;
	}
	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}
	public Float getTotal() {
		return total;
	}
	public void setTotal(Float total) {
		this.total = total;
	}
	public Float getPaid() {
		return paid;
	}
	public void setPaid(Float paid) {
		this.paid = paid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getDelivery() {
		return delivery;
	}
	public void setDelivery(Long delivery) {
		this.delivery = delivery;
	}
	public Timestamp getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
	public Long getProduct() {
		return product;
	}
	public void setProduct(Long product) {
		this.product = product;
	}
	public Timestamp getWanted_at() {
		return wanted_at;
	}
	public void setWanted_at(Timestamp wanted_at) {
		this.wanted_at = wanted_at;
	}
	public Long getAddress() {
		return address;
	}
	public void setAddress(Long address) {
		this.address = address;
	}
	public Timestamp getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", coupon=" + coupon + ", total=" + total + ", paid=" + paid + ", type=" + type
				+ ", status=" + status + ", delivery=" + delivery + ", address=" + address + ", product=" + product
				+ ", wanted_at=" + wanted_at + ", created_at=" + created_at + ", updated_at=" + updated_at + "]";
	}
}
