package com.generic.model;

import org.json.simple.JSONObject;

public abstract class PersistentJson implements IPersistentJson {
	
	protected String id;
	
	public abstract JSONObject toJSON();
	
	public String getId() {
		return id;
	}
}
