package dedalus.domain.structures;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dedalus.domain.Faculty;
import dedalus.domain.IdentifiableEntity;

public class EligibleFaculty extends EligibleEntry implements Serializable {
	private static final long serialVersionUID = 4590254361085206922L;
	
	private Faculty faculty;

	@JsonIgnore
	@Override
	public IdentifiableEntity getEntry() {
		return this.faculty;
	}
	
	@Override
	public void setEntry(IdentifiableEntity entry) {
		this.faculty = (Faculty)entry;
	}
	
	public EligibleFaculty() {
		super(false);
	}
	
	public EligibleFaculty(Faculty faculty) {
		super(false);
		this.faculty = faculty;
	}

	public EligibleFaculty(Faculty faculty, boolean selected) {
		super(selected);
		this.faculty = faculty;
		this.selected = selected;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}