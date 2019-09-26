package dedalus.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dedalus.ApplicationStringConstants;

@Entity
@Table(name="tokens")
public class Token extends IdentifiableEntity {
	private static final long serialVersionUID = 6587082256666365977L;
	
	@Column(name="value")
	private String value;

	@Column(name="batch")
	private String batch;
	
	@Column(name="creationDate")
	private Date creationDate;
	
	@Column(name="number")
	private Integer number;
	
	@Column(name="payed")
	private Boolean payed; 
	
	@Column(name="status")
	private Integer status;
	
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tcid")
	private TuitionCentre tuitionCentre;
	
	//@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "qnrid")
	private Questionnaire questionnaire;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pupilid")
	private Pupil pupil;

	@Column(name="assignDate")
	private Date assignDate;

	private transient boolean isAvailable;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Boolean getPayed() {
		return payed;
	}

	public void setPayed(Boolean payed) {
		this.payed = payed;
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

	public Pupil getPupil() {
		return pupil;
	}

	public void setPupil(Pupil pupil) {
		this.pupil = pupil;
	}

	public Date getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}

	public boolean isAvailable() {
		return this.pupil == null;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAssignDateAsString() {
		if (assignDate != null) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ApplicationStringConstants.DATE_FORMAT.getValue());
			
			return simpleDateFormat.format(assignDate);
		}
		
		return "";
	}
}