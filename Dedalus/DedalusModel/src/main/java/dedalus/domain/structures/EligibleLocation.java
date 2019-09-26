package dedalus.domain.structures;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dedalus.domain.IdentifiableEntity;
import dedalus.domain.Location;

public class EligibleLocation extends EligibleEntry implements Serializable {
	private static final long serialVersionUID = 4590254361085206922L;
	
	private Location location;

	@JsonIgnore
	@Override
	public IdentifiableEntity getEntry() {
		return this.location;
	}
	
	@Override
	public void setEntry(IdentifiableEntity entry) {
		this.location = (Location)entry;
	}
	
	public EligibleLocation() {
		super(false);
	}
	
	public EligibleLocation(Location location, boolean selected) {
		super(selected);
		this.location = location;
		this.selected = selected;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
