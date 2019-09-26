package org.DedalusDataInitiator.init.domain;

public class SameQuestion {
	private Question question;
	private Question sameQuestion;
	
	public SameQuestion(Question question, Question sameQuestion) {
		this.question = question;
		this.sameQuestion = sameQuestion;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Question getSameQuestion() {
		return sameQuestion;
	}

	public void setSameQuestion(Question sameQuestion) {
		this.sameQuestion = sameQuestion;
	}
}
