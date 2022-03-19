package com.project.pizzeria.utils.enumuration;

public enum OrderType {
	CASH_ON_DELIVERY("Cash on delivery"),
	RESERVATION("R�servation � nos restaurants");
	
	private final String label;

	OrderType(String string) {
		this.label=string;
	}
	
	public String getLabel()
	{
		return this.label;
	}
}
