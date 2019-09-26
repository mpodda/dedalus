package org.DedalusDataInitiator2.init.constants;

public enum ColumnIndex {
	Question(0),
	Holland1(1),
	Holland2(2),
	QuestionRepeat(3),
	Category1(4),
	Category2(5),
	Category3(6),
	ScientificField1(8),
	ScientificField2(9),
	ScientificField3(10)
	;
	
	private int index;
	
	private ColumnIndex(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return this.index;
	}
}
