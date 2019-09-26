package dedalus.domain.structures;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dedalus.domain.IdentifiableEntity;
import dedalus.domain.Location;
import dedalus.domain.University;

public class EligibleUniversity extends EligibleEntry implements Serializable {
	private static final long serialVersionUID = 4590254361085206922L;
	
	private University university;

	@JsonIgnore
	@Override
	public IdentifiableEntity getEntry() {
		return this.university;
	}
	
	@Override
	public void setEntry(IdentifiableEntity entry) {
		this.university = (University)entry;
	}
	
	public EligibleUniversity() {
		super(false);
	}
	
	public EligibleUniversity(University university, boolean selected) {
		super(selected);
		this.university = university;
		this.selected = selected;
	}

	public University getUniversity() {
		return university;
	}
	
	public void setUniversity(University university) {
		this.university = university;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
