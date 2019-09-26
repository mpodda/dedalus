package dedalus.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Parameter extends IdentifiableEntity {
	private static final long serialVersionUID = 8879576823395107723L;
	
	@Column(name="description")
	protected String description;

	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		
		if (this.id != null) {
			hashCode += this.id.hashCode();
		}
		
		if (this.description != null) {
			hashCode += this.description.hashCode();
		}
		
		return hashCode;		
		
	}
	
	
	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		
		/*
		if (!(object instanceof Holland)) {
			return false;
		}
		*/
		
		Parameter parameter = (Parameter)object;
		
		if (this.id != null && parameter.getId() != null) {
			return this.id.equals(parameter.getId());
		}
		
		if (this.description != null && parameter.getDescription() != null) {
			return this.description.equals(parameter.getDescription());
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return String.format("id = %s, version = %s, description = %s", id, version, description);
	}
}
