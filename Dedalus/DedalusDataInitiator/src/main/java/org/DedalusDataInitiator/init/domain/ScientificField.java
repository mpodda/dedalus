package org.DedalusDataInitiator.init.domain;

public class ScientificField {
	private int id;
	private int number;
	private String description;
	
	public ScientificField(int id, int number, String description) {
		this.id = id;
		this.number = number;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
