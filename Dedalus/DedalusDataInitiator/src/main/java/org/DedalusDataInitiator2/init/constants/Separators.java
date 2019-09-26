package org.DedalusDataInitiator2.init.constants;

public enum Separators {
	ColumnSeparator(";"),
	QuestionNumberToQuestionSeparator("."),
	RepeatQuestionSeparator(",");
	
	private String separator;
	
	private Separators (String separator) {
		this.separator = separator;
	}

	public String getSeparator() {
		return this.separator;
	}
}
