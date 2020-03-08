package com.generic.model;

public class Weight {
	public WeightType unit;
	public double amount;
	
	private enum WeightType {
		KG,
		LB
	}
}
