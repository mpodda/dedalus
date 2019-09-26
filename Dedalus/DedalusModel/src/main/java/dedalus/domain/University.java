package dedalus.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="universities")
public class University extends Parameter {
	private static final long serialVersionUID = -5360148707915093638L;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="universities_faculties",
            joinColumns=
            @JoinColumn(name="University_id", referencedColumnName="id"),
            inverseJoinColumns=
            @JoinColumn(name="faculty_id", referencedColumnName="id")
    )
    @JsonBackReference
    private Set<Faculty> faculties;

	public Set<Faculty> getFaculties() {
		return faculties;
	}

	public void setFaculties(Set<Faculty> faculties) {
		this.faculties = faculties;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		
		if (!(object instanceof University)) {
			return false;
		}
		
		if ( ((University)object).getId() == null || this.getId()==null) {
			return false;
		}
		
		return ((University)object).getId().equals(this.getId());
	}	
}
