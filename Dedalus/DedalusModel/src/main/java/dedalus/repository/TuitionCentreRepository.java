package dedalus.repository;

import javax.transaction.Transactional;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import dedalus.domain.TuitionCentre;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class TuitionCentreRepository extends ParameterRepository<TuitionCentre> {

	@Override
	protected Class<TuitionCentre> getEntityBean() {
		return TuitionCentre.class;
	}
	
	public TuitionCentre byUserName(String userName) {
		return (TuitionCentre)entityManager.createQuery("select tc from TuitionCentre tc where tc.userName = :userName")
		.setParameter("userName", userName)
		.getSingleResult();
	}

	public int countByUsername(String userName) {
		return ((Number)entityManager.createQuery("select count(tc) from TuitionCentre tc where tc.userName = :userName")
				.setParameter("userName", userName)
				.getSingleResult()).intValue();
	}
}