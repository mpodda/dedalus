package dedalus.domain.structures;

import java.io.Serializable;

import dedalus.domain.Pupil;
import dedalus.domain.Questionnaire;

public class SendTokenRequest implements Serializable {
	private static final long serialVersionUID = 4704810943453444795L;
	
	private Questionnaire questionnaire;
	private Pupil pupil;
	
	public SendTokenRequest() {
		
	}
	
	public SendTokenRequest(Questionnaire questionnaire, Pupil pupil) {
		this.questionnaire = questionnaire;
		this.pupil = pupil;
	}

	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

	public Pupil getPupil() {
		return pupil;
	}

	public void setPupil(Pupil pupil) {
		this.pupil = pupil;
	}
}
