package org.DedalusDataInitiator.init.domain;

public class QuestionnaireQuestion {
	private Questionnaire questionnaire;
	private Question question;
	
	public QuestionnaireQuestion(Questionnaire questionnaire, Question question) {
		this.questionnaire = questionnaire;
		this.question = question;
	}

	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
}
