package dedalus.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import dedalus.domain.Faculty;
import dedalus.domain.Location;
import dedalus.domain.University;

@Repository
@Scope( BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class FacultyRepository extends ParameterRepository<Faculty> {
	@Override
	protected Class<Faculty> getEntityBean() {
		return Faculty.class;
	}
	
	public List<Location> getFacultyLocations(Long id) {
		return entityManager.createQuery("select distinct location from Location location join location.faculies facs where facs.id=:id")
				.setParameter("id", id)
			    .getResultList();
	}
	
	public List<University> getFacultyUniversities(Long id) {
		return entityManager.createQuery("select distinct university from University university join university.faculties facs where facs.id=:id")
				.setParameter("id", id)
			    .getResultList();
	}
}
