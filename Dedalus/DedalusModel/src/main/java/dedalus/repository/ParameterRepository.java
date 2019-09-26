package dedalus.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import dedalus.domain.Parameter;

@Repository
@Scope( BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public abstract class ParameterRepository<P extends Parameter> extends AbstractJpaDao<P> {
	
	//TODO: Remove later
	public P createEntity() {
		try {
			return this.getEntityBean().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			return null;
		}
	}
	
	public Iterable<P> findByDescription(String description) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<P> query = criteriaBuilder.createQuery(this.getEntityBean());
		Root<P> root = query.from(this.getEntityBean());
		
		Predicate descriptionPredicate = criteriaBuilder.equal(root.get("description"), description);
		query.where(descriptionPredicate);
		
		return entityManager.createQuery(query.select(root)).getResultList();
	}
}
