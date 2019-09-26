package dedalus.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="hollands")
public class Holland extends Parameter {
	private static final long serialVersionUID = 2161179614675188236L;

	@Column(name="value", unique=true)
	private Integer value;

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		
		if (this.id != null) {
			hashCode += this.id.hashCode();
		}
		
		if (this.value != null) {
			hashCode += this.value.hashCode();
		}
		
		return hashCode;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		
		if (!(object instanceof Holland)) {
			return false;
		}
		
		Holland holland = (Holland)object;
		
		if (this.id != null && holland.getId() != null) {
			return this.id.equals(holland.getId());
		}
		
		if (this.value != null && holland.getValue() != null) {
			return this.value.equals(holland.getValue());
		}
		
		return false;
	}
	
//	@JsonBackReference
//	@OneToMany(fetch=FetchType.LAZY, mappedBy="holland1")
//	private Set<Question> questionsForHolland1;
//	
//	@JsonBackReference
//	@OneToMany(fetch=FetchType.LAZY, mappedBy="holland2")
//	private Set<Question> questionsForHolland2;
//
//	public Set<Question> getQuestionsForHolland1() {
//		return questionsForHolland1;
//	}
//
//	public void setQuestionsForHolland1(Set<Question> questionsForHolland1) {
//		this.questionsForHolland1 = questionsForHolland1;
//	}
//
//	public Set<Question> getQuestionsForHolland2() {
//		return questionsForHolland2;
//	}
//
//	public void setQuestionsForHolland2(Set<Question> questionsForHolland2) {
//		this.questionsForHolland2 = questionsForHolland2;
//	}
	
}
