package dedalus.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="answers")
public class Answer extends IdentifiableEntity {
	private static final long serialVersionUID = -4204385649807970465L;

	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "token_id")
	private Token token;
	
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
	private Question question;
	
	
	@Column(name="value")
	private Integer value;

	@Column(name="step")
	private Integer step;
	
	@Column(name="number")
	private Integer number;
	
	public Answer() {
		
	}
	
	public Answer(Integer value) {
		this.value = value;
	}	

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
}