package dedalus.domain.structures;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dedalus.domain.IdentifiableEntity;
import dedalus.domain.Question;

public class EligibleQuestion extends EligibleEntry implements Serializable {
	private static final long serialVersionUID = 5322238241290473854L;
	
	private Question question;

	@JsonIgnore
	@Override
	public IdentifiableEntity getEntry() {
		return this.question;
	}

	@Override
	public void setEntry(IdentifiableEntity entry) {
		this.question = (Question)entry;
	}
	
	public EligibleQuestion() {
		super(false);
	}

	public EligibleQuestion(Question question, boolean selected) {
		super(selected);
		this.question = question;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
}
