package dedalus.domain.structures;

import java.io.Serializable;

import dedalus.domain.IdentifiableEntity;

public abstract class EligibleEntry implements Serializable {
	private static final long serialVersionUID = 8828851785619595480L;
	
	protected boolean selected;
	
	public abstract IdentifiableEntity getEntry();
	public abstract void setEntry(IdentifiableEntity entry);
	
	public EligibleEntry(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
