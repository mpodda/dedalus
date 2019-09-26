package dedalus.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="scientific_fields")
public class ScientificField extends Parameter {
	private static final long serialVersionUID = -7090800204847072234L;
	
	@Column(name="number")
	private Integer number;
	
	public ScientificField() {
		
	}
	
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	
	@Override
	public boolean equals(Object object) {
		
		if (!(object instanceof ScientificField)) {
			return false;
		}
		
		ScientificField scientificField =  (ScientificField)object;
		
		if (this.number != null && scientificField.getNumber() != null) {
			return this.number.equals(scientificField.getNumber());
		}
		
		
		return super.equals(object);
	}

	
}
