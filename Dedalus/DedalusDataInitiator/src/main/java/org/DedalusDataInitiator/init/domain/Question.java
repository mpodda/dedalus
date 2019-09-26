package org.DedalusDataInitiator.init.domain;

import java.util.Optional;

public class Question {
	private int id;
	private String description;
	private int number;
	private int category1Id;
	private String category1Description;
	private int category2Id;
	private String category2Description;
	private int category3Id;
	private String category3Description;
	private int holland1;
	private int holland2;
	private int scientificField1;
	private int scientificField2;
	private int scientificField3;
	private int scientificField4;
	
	
	public Question(int id, int number) {
		this.id = id;
		this.number = number;
	}

	public Question(int id, String description, int number, int category1Id, int category2Id, int category3Id,
			int holland1, int holland2, int scientificField1, int scientificField2, int scientificField3,
			int scientificField4) {
		
		this.id = id;
		this.description = description;
		this.number = number;
		this.category1Id = category1Id;
		this.category2Id = category2Id;
		this.category3Id = category3Id;
		this.holland1 = holland1;
		this.holland2 = holland2;
		this.scientificField1 = scientificField1;
		this.scientificField2 = scientificField2;
		this.scientificField3 = scientificField3;
		this.scientificField4 = scientificField4;
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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Optional<Integer> getCategory1Id() {
		if (category1Id==0) {
			return Optional.empty();
		}
		
		return Optional.of(category1Id);
	}

	public void setCategory1Id(int category1Id) {
		this.category1Id = category1Id;
	}

	public String getCategory1Description() {
		return category1Description;
	}

	public void setCategory1Description(String category1Description) {
		this.category1Description = category1Description;
	}

	public Optional<Integer> getCategory2Id() {
		if (category2Id==0) {
			return Optional.empty();
		}
		
		return Optional.of(category2Id);
	}

	public void setCategory2Id(int category2Id) {
		this.category2Id = category2Id;
	}

	public String getCategory2Description() {
		return category2Description;
	}

	public void setCategory2Description(String category2Description) {
		this.category2Description = category2Description;
	}

	public Optional<Integer> getCategory3Id() {
		if (category3Id==0) {
			return Optional.empty();
		}
		
		return Optional.of(category3Id);
	}

	public void setCategory3Id(int category3Id) {
		this.category3Id = category3Id;
	}

	public String getCategory3Description() {
		return category3Description;
	}

	public void setCategory3Description(String category3Description) {
		this.category3Description = category3Description;
	}

	public Optional<Integer> getHolland1() {
		if (holland1==0) {
			return Optional.empty();
		}
		
		return Optional.of(holland1);
	}

	public void setHolland1(int holland1) {
		this.holland1 = holland1;
	}

	public Optional<Integer> getHolland2() {
		if (holland2==0) {
			return Optional.empty();
		}
		
		return Optional.of(holland2);		
	}

	public void setHolland2(int holland2) {
		this.holland2 = holland2;
	}

	public Optional<Integer> getScientificField1() {
		if (scientificField1==0) {
			return Optional.empty();
		}
		
		return Optional.of(scientificField1);		
	}

	public void setScientificField1(int scientificField1) {
		this.scientificField1 = scientificField1;
	}

	public Optional<Integer> getScientificField2() {
		if (scientificField2==0) {
			return Optional.empty();
		}
		
		return Optional.of(scientificField2);		
	}

	public void setScientificField2(int scientificField2) {
		this.scientificField2 = scientificField2;
	}

	public Optional<Integer> getScientificField3() {
		if (scientificField3==0) {
			return Optional.empty();
		}
		
		return Optional.of(scientificField3);		
	}

	public void setScientificField3(int scientificField3) {
		this.scientificField3 = scientificField3;
	}

	public Optional<Integer> getScientificField4() {
		if (scientificField4==0) {
			return Optional.empty();
		}
		
		return Optional.of(scientificField4);		
	}

	public void setScientificField4(int scientificField4) {
		this.scientificField4 = scientificField4;
	}
	
}
