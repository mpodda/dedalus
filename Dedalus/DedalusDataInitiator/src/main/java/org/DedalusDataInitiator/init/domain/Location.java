package org.DedalusDataInitiator.init.domain;

public class Location {
	private int id;
	private String description;
	private boolean hasSameLocation = false;

	public Location() {
		
	}
	
	public Location(int id, String description) {
		this.id = id;
		this.description = description;
	}

	public Location(int id, String description, boolean hasSameLocation) {
		this.id = id;
		this.description = description;
		this.hasSameLocation = hasSameLocation;
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

	public boolean isHasSameLocation() {
		return hasSameLocation;
	}

	public void setHasSameLocation(boolean hasSameLocation) {
		this.hasSameLocation = hasSameLocation;
	}
}
