package org.DedalusDataInitiator.init.domain;

public class Faculty {
	private int id;
	private String description;
	
	public Faculty() {
		
	}

	public Faculty(int id, String description) {
		this.id = id;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
