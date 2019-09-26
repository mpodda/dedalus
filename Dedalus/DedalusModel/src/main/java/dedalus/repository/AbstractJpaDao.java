package dedalus.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public abstract class AbstractJpaDao<T extends Serializable> {
	@PersistenceContext
	protected EntityManager entityManager;
	 
	protected abstract Class<T> getEntityBean();
	
	public T createEntity() {
		try {
			return this.getEntityBean().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			return null;
		}
	}	
	public T findOne(Long id){
	   return entityManager.find(this.getEntityBean(), id);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAll(){
		//System.out.println(String.format("entityBeanType = %s", this.getEntityBean().getName()));
		
	   return entityManager.createQuery( "from " + this.getEntityBean().getName())
	    .getResultList();
	}
	 
	public void save(T entity){
	   entityManager.persist(entity);
	}
	 
	public void update(T entity){
	   entityManager.merge(entity);
	}
	 
	public T merge(T entity) {
		return entityManager.merge(entity);
	} 
	
	public void delete( T entity ){
	   entityManager.remove( entity );
	}
	
	public void deleteById( Long entityId ){
	   T entity = findOne( entityId );
	   delete( entity );
	}
}