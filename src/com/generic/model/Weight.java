package com.generic.model;

public class Weight {
	private WeightType unit;
	private double weight;
	
	private enum WeightType {
		KG,
		LB
	}
}
