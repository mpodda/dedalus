package dedalus.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import dedalus.domain.Faculty;
import dedalus.domain.University;

@Repository
@Scope( BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class UniversityRepository extends ParameterRepository<University> {

	@Override
	protected Class<University> getEntityBean() {
		return University.class;
	}

	public List<Faculty> getUniversityFaculties(Long id) {
		return entityManager.createQuery("select distinct faculty from Faculty faculty join faculty.universities universities where universities.id=:id")
				.setParameter("id", id)
			    .getResultList();
	}
	
}
