package dedalus.pupil.domain;

public enum AnswerValue {
	Grade0(0),
	Grade1(1),
	Grade2(2),
	Grade3(3),
	Grade4(4);
	
	private Integer value;
	
	private AnswerValue(Integer value) {
		this.value = value;
	}
	
	public Integer getValue() {
		return value;
	}
	
	public static final AnswerValue maxGrade() {
		return AnswerValue.Grade4;
	}
}