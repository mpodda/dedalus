package dedalus.service.api;

import java.util.ArrayList;
import java.util.List;

import dedalus.domain.IdentifiableEntity;
import dedalus.repository.AbstractJpaDao;

public abstract class IdentifiableEntityService <E extends IdentifiableEntity> {
	public abstract AbstractJpaDao <E> getRepository();
	
	public E create() {
		return this.getRepository().createEntity();
	}	
	
	public E save (E entity) {
		return this.getRepository().merge(entity);
	}
	
	public void delete(E entity) {
		this.getRepository().delete(this.getRepository().findOne(entity.getId()));
	} 
	
	public List<E> findAll() {
		List<E> list = new ArrayList<>();
		
		this.getRepository().findAll().forEach (
			e -> list.add((E) e)
		);
		
		return list;
	}
	
	public E get (E entity) {
		return this.getRepository().findOne(entity.getId());
	}
	
	public E findOne(Long id) {
		return this.getRepository().findOne(id);
	}
}
