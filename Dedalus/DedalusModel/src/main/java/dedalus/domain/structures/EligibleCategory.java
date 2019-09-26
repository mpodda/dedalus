package dedalus.domain.structures;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dedalus.domain.Category;
import dedalus.domain.IdentifiableEntity;

public class EligibleCategory extends EligibleEntry implements Serializable {
	private static final long serialVersionUID = 7327665603103843312L;
	
	private Category category;
	
	public EligibleCategory() {
		super(false);
	}

	public EligibleCategory(boolean selected, Category category) {
		super(selected);
		this.category = category;
	}
	
	@JsonIgnore
	@Override
	public IdentifiableEntity getEntry() {
		return this.category;
	}

	@Override
	public void setEntry(IdentifiableEntity entry) {
		this.category = (Category)entry;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
