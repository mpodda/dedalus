package dedalus.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="locations")
public class Location extends Parameter {
	private static final long serialVersionUID = 3291536425395568169L;
	
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="faculties_locations",
            joinColumns=
            @JoinColumn(name="location_id", referencedColumnName="id"),
            inverseJoinColumns=
            @JoinColumn(name="faculty_id", referencedColumnName="id")
    )
    
	private Set<Faculty> faculies;
	
	public Set<Faculty> getFaculies() {
		return faculies;
	}
	
	public void setFaculies(Set<Faculty> faculies) {
		this.faculies = faculies;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		
		if (!(object instanceof Location)) {
			return false;
		}
		
		if ( ((Location)object).getId() == null || this.getId()==null) {
			return false;
		}
		
		return ((Location)object).getId().equals(this.getId());
	}
}
