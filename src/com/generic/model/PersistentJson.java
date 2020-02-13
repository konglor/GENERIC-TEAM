package com.generic.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class PersistentJson implements IPersistentJson {
	
	protected String id;
	
	public abstract JSONObject toJSON();
	
	public String getId() {
		return id;
	}
	
	public void save(String filename) {
		String filePath = "output/"+ filename;
		File file = new File(filePath);
		
		// prettify the json output
		ObjectMapper mapper = new ObjectMapper();
		
		// Check and create directory
		if (!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		
		//Write JSON file
		try (FileWriter fw = new FileWriter(filePath)) {
			PrintWriter printWriter = new PrintWriter(fw);
			String json = mapper
					.writerWithDefaultPrettyPrinter()
					.writeValueAsString(toJSON());

			printWriter.println(json);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
