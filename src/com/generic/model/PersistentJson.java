package com.generic.model;

import org.json.simple.JSONObject;

public abstract class PersistentJson {
	
	protected String id;
	
	public abstract JSONObject toJSON();
	
	public String getId() {
		return id;
	}
}
