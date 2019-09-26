package dedalus.domain.structures;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dedalus.domain.IdentifiableEntity;
import dedalus.domain.Questionnaire;

public class EligibleQuestionnaire extends EligibleEntry implements Serializable {
	private static final long serialVersionUID = 3028742193023808974L;
	
	private Questionnaire questionnaire;

	public EligibleQuestionnaire() {
		super(false);
	}
	
	
	public EligibleQuestionnaire(boolean selected, Questionnaire questionnaire) {
		super(selected);
		this.questionnaire = questionnaire;
	}


	public EligibleQuestionnaire(Questionnaire questionnaire) {
		super(false);
		this.questionnaire = questionnaire;
	}

	@JsonIgnore
	@Override
	public IdentifiableEntity getEntry() {
		return this.questionnaire;
	}

	@Override
	public void setEntry(IdentifiableEntity entry) {
		this.questionnaire = (Questionnaire)entry;
	}

	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}
	
	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}
}
