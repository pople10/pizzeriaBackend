package com.project.pizzeria.utils.enumuration;

public enum OrderType {
	CASH_ON_DELIVERY("Cash on delivery"),
	RESERVATION("Réservation à nos restaurants");
	
	private final String label;

	OrderType(String string) {
		this.label=string;
	}
	
	public String getLabel()
	{
		return this.label;
	}
}
