package dedalus.enums;

public enum TokenStatus {
	Available(0), // Not Assigned to Pupil
	NotStarted(1), // Assigned to Pupil, but no answer action
	OnGoing(2), // Pupil has started to answer questions
	Completed(3); // All questions are answered and pupil has confirm completion
	
	private Integer status;
	
	private TokenStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getStatus() {
		return status;
	}
}
