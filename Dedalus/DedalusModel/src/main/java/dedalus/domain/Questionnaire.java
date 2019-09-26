package dedalus.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="questionnaires")
public class Questionnaire extends Parameter {
	private static final long serialVersionUID = -3299501089965598251L;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name="questionnaires_questions",
            joinColumns=
            @JoinColumn(name="questionnaire_id", referencedColumnName="id"),
            inverseJoinColumns=
            @JoinColumn(name="question_id", referencedColumnName="id")
    )
    //@JsonBackReference
    @JsonIgnore
    private Set<Question> questions;

	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, mappedBy="questionnaire")
	private Set<Token> tokens;
    
    @JsonIgnore
	public Set<Question> getQuestions() {
		return questions;
	}

    @JsonIgnore
	public void setQuestions(Set<Question> questions) {
		this.questions = questions; 
	}

	@Column(name="questionsPerStep")
	private Integer questionsPerStep;
    
    public Integer getQuestionsPerStep() {
		return questionsPerStep;
	}

	public void setQuestionsPerStep(Integer questionsPerStep) {
		this.questionsPerStep = questionsPerStep;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}

		if (!(object instanceof Questionnaire)) {
			return false;
		}

		Questionnaire questionnaire = (Questionnaire) object;

		if (questionnaire.getId() == null) {
			return false;
		}

		return questionnaire.getId().equals(this.getId());
	}

}
