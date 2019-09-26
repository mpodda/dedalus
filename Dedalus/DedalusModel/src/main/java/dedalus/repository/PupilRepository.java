package dedalus.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import dedalus.domain.Pupil;
import dedalus.domain.Token;

@Repository
@Scope( BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class PupilRepository extends AbstractJpaDao<Pupil> {

	@Override
	protected Class<Pupil> getEntityBean() {
		return Pupil.class;
	}
	
	@SuppressWarnings("unchecked")
	public List<Pupil> byTuitionCentre(Long tuitionCentreId) {
		return entityManager.createQuery("select distinct pupil from Pupil pupil where pupil.tuitionCentre.id=:tcId order by pupil.name")
				.setParameter("tcId", tuitionCentreId)
			    .getResultList();
	}
	

}
