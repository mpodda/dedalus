package dedalus;

public enum  ApplicationStringConstants {
	BATCH_CODE_PREFIX("B"),
	DATE_FORMAT("dd/MM/yyyy"),
	DATETIME_FORMAT("dd/MM/yyyy hh:mm:ss"),
	TOP_CATEGORIES_FOR_CHART("4"),
	TOP_CATEGORIES_FOR_LIST("30");
	
	private String value;
	
	private ApplicationStringConstants(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public Integer getIntValue() {
		try {
			return Integer.parseInt(this.value);
		} catch (Exception e) {
			return 0;
		}
	}
}
