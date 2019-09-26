package dedalus.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="questions")
public class Question extends Parameter {
	private static final long serialVersionUID = -6746584709578577594L;
	
	@Column(name="value")
	private Integer number;

	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category")
	private Category category;
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category2")
	private Category category2;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="subCategory")
	private Category subCategory;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="holland1")
	private Holland holland1;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="holland2")
	private Holland holland2;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="scientificField1")
	private ScientificField scientificField1;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="scientificField2")
	private ScientificField scientificField2;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="scientificField3")
	private ScientificField scientificField3;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="scientificField4")
	private ScientificField scientificField4;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name="questionnaires_questions",
            joinColumns=
            @JoinColumn(name="question_id", referencedColumnName="id"),
            inverseJoinColumns=
            @JoinColumn(name="questionnaire_id", referencedColumnName="id")
    )
    private Set<Questionnaire> questionnaires;
    
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="questions_faculties",
            joinColumns=
            @JoinColumn(name="question_id", referencedColumnName="id"),
            inverseJoinColumns=
            @JoinColumn(name="faculty_id", referencedColumnName="id")
    )
    private Set<Faculty> faculties;
    
    @JsonIgnore
    //@OneToMany(fetch = FetchType.LAZY)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="question_same_questions",
            joinColumns=
            @JoinColumn(name="question_id", referencedColumnName="id", unique=false),
            inverseJoinColumns=
            @JoinColumn(name="same_question_id", referencedColumnName="id", unique=false)
    )
    private Set<Question> sameQuestions;
    
    
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Category getCategory2() {
		return category2;
	}

	public void setCategory2(Category category2) {
		this.category2 = category2;
	}

	public Category getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(Category subCategory) {
		this.subCategory = subCategory;
	}

	public Holland getHolland1() {
		return holland1;
	}

	public void setHolland1(Holland holland1) {
		this.holland1 = holland1;
	}

	public Holland getHolland2() {
		return holland2;
	}

	public void setHolland2(Holland holland2) {
		this.holland2 = holland2;
	}

	public ScientificField getScientificField1() {
		return scientificField1;
	}

	public void setScientificField1(ScientificField scientificField1) {
		this.scientificField1 = scientificField1;
	}

	public ScientificField getScientificField2() {
		return scientificField2;
	}

	public void setScientificField2(ScientificField scientificField2) {
		this.scientificField2 = scientificField2;
	}

	public ScientificField getScientificField3() {
		return scientificField3;
	}

	public void setScientificField3(ScientificField scientificField3) {
		this.scientificField3 = scientificField3;
	}

	public ScientificField getScientificField4() {
		return scientificField4;
	}

	public void setScientificField4(ScientificField scientificField4) {
		this.scientificField4 = scientificField4;
	}

	public Set<Questionnaire> getQuestionnaires() {
		return questionnaires;
	}

	public void setQuestionnaires(Set<Questionnaire> questionnaires) {
		this.questionnaires = questionnaires;
	}

	public Set<Faculty> getFaculties() {
		return faculties;
	}

	public void setFaculties(Set<Faculty> faculties) {
		this.faculties = faculties;
	}

	public Set<Question> getSameQuestions() {
		return sameQuestions;
	}

	public void setSameQuestions(Set<Question> sameQuestions) {
		this.sameQuestions = sameQuestions;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		
		if (!(object instanceof Question)) {
			return false;
		}
		
		if ( ((Question)object).getId() == null || this.getId()==null) {
			return false;
		}
		
		return ((Question)object).getId().equals(this.getId());
	}
}