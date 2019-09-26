package dedalus.domain.search;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dedalus.ApplicationStringConstants;
import dedalus.domain.Pupil;
import dedalus.domain.TuitionCentre;

public class SearchTokenCriteria implements Serializable {
	private static final long serialVersionUID = 2483520872889964972L;
	
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat(ApplicationStringConstants.DATE_FORMAT.getValue());
	private final static SimpleDateFormat dateTimeFormat = new SimpleDateFormat(ApplicationStringConstants.DATETIME_FORMAT.getValue());
	
	public enum ParameterNames {
		TUITION_CENTRE_ID("tcId"),
		CREATION_DATE_FROM("creationDateFrom"),
		CREATION_DATE_TO("creationDateTo"),
		PAYED("payed"),
		AVAILABLE("available"),
		PUPIL_ID("pupilId");

		
		private String name;
		
		private ParameterNames(String name) {
			this.name = name;
		}
		
		public String parameterName() {
			return name;
		}
	}
	
	TuitionCentre tuitionCentre;
	Date creationDateFrom;
	Date creationDateTo;
	Boolean payed;
	Boolean avaiable;
	Pupil pupil;

	public SearchTokenCriteria() {
		
	}

	
	public SearchTokenCriteria(TuitionCentre tuitionCentre) {
		this.tuitionCentre = tuitionCentre;
	}

	public String getCreationDateFromAsString() {
		return dateFormat.format(creationDateFrom);
	} 
	
	public String getCreationDateToAsString() {
		return dateFormat.format(creationDateTo);
	}
	
	public void setCreationDateFromAsString(String date) {
		date = String.format("%s %s", date, "00:00:00");
		
		try {
			this.creationDateFrom = dateTimeFormat.parse(date);
		} catch (ParseException e) {
			System.err.println(String.format("setCreationDateFromAsString::Error: %s", e.getMessage()));
		}
	}
	
	public void setCreationDateToAsString (String date) {
		date = String.format("%s %s", date, "23:59:59");
		
		try {
			this.creationDateTo = dateTimeFormat.parse(date);
		} catch (ParseException e) {
			System.err.println(String.format("setCreationDateToAsString::Error: %s", e.getMessage()));
		}
	}
	
	
	public void init() {
		tuitionCentre = null;
		creationDateFrom = null;
		creationDateTo = null;
		payed = null;
		avaiable = null;
		pupil = null;
	} 
	
	public boolean hasTuitionCentre() {
		return tuitionCentre != null;
	}
	
	public boolean hasCreationDateFrom () {
		return creationDateFrom != null;
	}
	
	public boolean hasCreationDateTo () {
		return creationDateTo != null;
	}
	
	public boolean hasPayed () {
		return payed != null;
	}

	public  boolean hasAvailable() {
		return avaiable != null;
	}

	public boolean hasPupil() {
		return pupil != null;
	}

	public TuitionCentre getTuitionCentre() {
		return tuitionCentre;
	}

	public void setTuitionCentre(TuitionCentre tuitionCentre) {
		this.tuitionCentre = tuitionCentre;
	}

	public Date getCreationDateFrom() {
		return creationDateFrom;
	}

	public void setCreationDateFrom(Date creationDateFrom) {
		this.creationDateFrom = creationDateFrom;
	}

	public Date getCreationDateTo() {
		return creationDateTo;
	}

	public void setCreationDateTo(Date creationDateTo) {
		this.creationDateTo = creationDateTo;
	}

	public Boolean getPayed() {
		return payed;
	}

	public void setPayed(Boolean payed) {
		this.payed = payed;
	}

	public Boolean getAvaiable() {
		return avaiable;
	}

	public void setAvaiable(Boolean avaiable) {
		this.avaiable = avaiable;
	}

	public Pupil getPupil() {
		return pupil;
	}

	public void setPupil(Pupil pupil) {
		this.pupil = pupil;
	}
}
