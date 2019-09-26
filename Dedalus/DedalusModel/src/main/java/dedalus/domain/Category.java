package dedalus.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="categories")
public class Category extends Parameter {
	private static final long serialVersionUID = 8445779944648785404L;

	
//	@Override
//	public boolean equals(Object object) {
//		if (object == null) {
//			return false;
//		}
//
//		if (!(object instanceof Category)) {
//			return false;
//		}
//
//		Category category = (Category) object;
//
//		if (category.getId() == null) {
//			return false;
//		}
//
//		return category.getId().equals(this.getId());
//	}
	
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Category)) {
			return false;
		}
		
		return super.equals(object);
	}
	
}