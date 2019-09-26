package dedalus.pupil.domain;

import java.io.Serializable;

public class Step implements Serializable {
	private static final long serialVersionUID = -6153963617565826892L;
	
	private Integer stepNo;
	private Boolean isCurrent;
	
	public Step(Integer stepNo, Boolean isCurrent) {
		this.stepNo = stepNo;
		this.isCurrent = isCurrent;
	}
	
	public Integer getStepNo() {
		return stepNo;
	}
	public void setStepNo(Integer stepNo) {
		this.stepNo = stepNo;
	}
	public Boolean getIsCurrent() {
		return isCurrent;
	}
	public void setIsCurrent(Boolean isCurrent) {
		this.isCurrent = isCurrent;
	}

}
