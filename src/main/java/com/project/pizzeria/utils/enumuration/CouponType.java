package com.project.pizzeria.utils.enumuration;

public enum CouponType {
	POURENCTAGE("Pourcentage"),
	CASH("Fonds");
	
	private final String label;

	CouponType(String string) {
		this.label=string;
	}
	
	public String getLabel()
	{
		return this.label;
	}
}
