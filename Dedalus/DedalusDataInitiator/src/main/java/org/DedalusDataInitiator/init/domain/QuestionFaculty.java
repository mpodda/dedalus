package org.DedalusDataInitiator.init.domain;

public class QuestionFaculty {
	private Question question;
	private Faculty faculty;
	
	public QuestionFaculty(Question question, Faculty faculty) {
		this.question = question;
		this.faculty = faculty;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}
}
