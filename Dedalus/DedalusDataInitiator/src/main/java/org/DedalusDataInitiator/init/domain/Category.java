package org.DedalusDataInitiator.init.domain;

public class Category {
	private int id;
	private String description;
	private Category sameCategory;
	
	public Category(int id, String description) {
		this.id = id;
		this.description = description;
		this.sameCategory = null;
	}
	
	public Category(int id, String description, Category sameCategory) {
		this.id = id;
		this.description = description;
		this.sameCategory = sameCategory;
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
	public Category getSameCategory() {
		return sameCategory;
	}
	public void setSameCategory(Category sameCategory) {
		this.sameCategory = sameCategory;
	}
}