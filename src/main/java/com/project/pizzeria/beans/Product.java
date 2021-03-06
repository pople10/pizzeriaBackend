package com.project.pizzeria.beans;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.pizzeria.beans.generic.GenericBean;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product extends GenericBean {
	private Long id;
	private String title;
	private String description;
	private String photo;
	private Float price;
	private Long preparation_time_in_min;
	private Boolean availability;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Long getPreparation_time_in_min() {
		return preparation_time_in_min;
	}
	public void setPreparation_time_in_min(Long preparation_time_in_min) {
		this.preparation_time_in_min = preparation_time_in_min;
	}
	public Boolean getAvailability() {
		return availability;
	}
	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", title=" + title + ", description=" + description + ", photo=" + photo
				+ ", price=" + price + ", preparation_time_in_min=" + preparation_time_in_min + ", availability="
				+ availability + "]";
	}
}
