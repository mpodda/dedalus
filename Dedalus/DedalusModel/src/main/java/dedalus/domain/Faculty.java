package dedalus.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="faculties")
public class Faculty extends Parameter {
	private static final long serialVersionUID = -4808496278464982031L;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="faculties_locations",
            joinColumns=
            @JoinColumn(name="faculty_id", referencedColumnName="id"),
            inverseJoinColumns=
            @JoinColumn(name="location_id", referencedColumnName="id")
    )
    
    @JsonBackReference
	private Set<Location> locations;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="universities_faculties",
            joinColumns=
            @JoinColumn(name="faculty_id", referencedColumnName="id"),
            inverseJoinColumns=
            @JoinColumn(name="University_id", referencedColumnName="id")
    )
//    @JsonBackReference
    private Set<University> universities;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="questions_faculties",
            joinColumns=
            @JoinColumn(name="faculty_id", referencedColumnName="id"),
            inverseJoinColumns=
            @JoinColumn(name="question_id", referencedColumnName="id")
    )
    //@JsonBackReference
    @JsonIgnore
    private Set<Question> questions;
    
	public Set<Location> getLocations() {
		return locations;
	}
	
	public void setLocations(Set<Location> locations) {
		this.locations = locations;
	}

	public Set<University> getUniversities() {
		return universities;
	}

	public void setUniversities(Set<University> universities) {
		this.universities = universities;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		
		if (!(object instanceof Faculty)) {
			return false;
		}
		
		if ( ((Faculty)object).getId() == null || this.getId()==null) {
			return false;
		}
		
		return ((Faculty)object).getId().equals(this.getId());
	}	
}
