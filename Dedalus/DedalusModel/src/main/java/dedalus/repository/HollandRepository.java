package dedalus.repository;

import javax.transaction.Transactional;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import dedalus.domain.Holland;

@Repository
@Scope( BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class HollandRepository extends ParameterRepository<Holland> {

	@Override
	protected Class<Holland> getEntityBean() {
		return Holland.class;
	}
	
	public Holland getByValue(Integer value) {
		return (Holland)entityManager.createQuery("select distinct holland from Holland holland where holland.value=:value")
				.setParameter("value", value)
			    .getSingleResult();
	}
}