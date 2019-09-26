package dedalus.domain.structures;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import dedalus.domain.Questionnaire;
import dedalus.domain.TuitionCentre;

public class TokenBatch implements Serializable{
	private static final long serialVersionUID = 3189727968985599299L;
	
	private String batchCode;
	private Integer amount;
	private TuitionCentre tuitionCentre;
	private Questionnaire questionnaire;
	private Date creationDate;
	private Boolean payed; 
	
	public TokenBatch() {
		
	}
	
	public TokenBatch(String batchCode, Integer amount, TuitionCentre tuitionCentre, Questionnaire questionnaire,
			Date creationDate, Boolean payed) {
		
		this.batchCode = batchCode;
		this.amount = amount;
		this.tuitionCentre = tuitionCentre;
		this.questionnaire = questionnaire;
		this.creationDate = creationDate;
		this.payed = payed;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public TuitionCentre getTuitionCentre() {
		return tuitionCentre;
	}

	public void setTuitionCentre(TuitionCentre tuitionCentre) {
		this.tuitionCentre = tuitionCentre;
	}

	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Boolean getPayed() {
		return payed;
	}

	public void setPayed(Boolean payed) {
		this.payed = payed;
	}
	
	private TokenBatch (Builder builder) {
		this.batchCode = builder.batchCode;
		this.creationDate = builder.creationDate;
		this.tuitionCentre = builder.tuitionCentre;
		this.payed = builder.payed;
	}

	
	/* Builder */
	public static class Builder {
		private TuitionCentre tuitionCentre;
		private Date creationDate;
		private String batchCode;
		private Boolean payed;
		
		public Builder(TuitionCentre tuitionCentre) {
			this.tuitionCentre = tuitionCentre;
			this.creationDate = new Date();
			this.batchCode = UUID.randomUUID().toString();
			this.payed = false;
		}

		public Builder(TuitionCentre tuitionCentre, String batchCode) {
			this.tuitionCentre = tuitionCentre;
			this.creationDate = new Date();
			this.batchCode = batchCode;
			this.payed = false;
		}
		
		public TokenBatch build(){
			return new TokenBatch(this);
		}
	}
}
