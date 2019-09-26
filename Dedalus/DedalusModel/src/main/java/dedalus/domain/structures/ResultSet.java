package dedalus.domain.structures;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import dedalus.service.ApplicationConstantsService;

public class ResultSet<T> implements Serializable {
	private static final long serialVersionUID = -2075779102378558301L;
	
	T subject;
	Integer value;
	Double percentage = new Double(0);
	Integer questionsRelatedToSubject;
	
	@Autowired
	ApplicationConstantsService applicationConstantsService;
	
	public ResultSet() {
		
	}

	public ResultSet(T subject, Integer value, Double percentage) {
		this.subject = subject;
		this.value = value;
		this.percentage = percentage;
	}
	
	public ResultSet(T subject, Integer value, Double percentage, Integer questionsRelatedToSubject) {
		this.subject = subject;
		this.value = value;
		this.percentage = percentage;
		this.questionsRelatedToSubject = questionsRelatedToSubject;
	}	
	
	public ResultSet(T subject) {
		this.subject = subject;
	}

	public T getSubject() {
		return subject;
	}

	public void setSubject(T subject) {
		this.subject = subject;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Double getPercentage() {
		if (this.questionsRelatedToSubject > 0) {
			return (100.00 * this.value) / this.questionsRelatedToSubject;
		}
		
		return new Double(0.0);
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public void setQuestionsRelatedToSubject(Integer questionsRelatedToSubject) {
		this.questionsRelatedToSubject = questionsRelatedToSubject;
	}

	public Integer getQuestionsRelatedToSubject() {
		return questionsRelatedToSubject;
	}
	
	public String getPercentageFormated() {
		return ApplicationConstantsService.numberFormatter().format(this.getPercentage());
	}

	@Override
	public String toString() {
		return new StringBuilder()
				.append("subject ").append(" = ").append("[").append(subject).append("]")
				.append(" value ").append(" = ").append(value)
				.append(" percentage ").append(" = ").append(percentage)
				.append(" questionsRelatedToSubject ").append(" = ").append(questionsRelatedToSubject)
				.toString();
	} 
}