package dedalus.repository;

import javax.transaction.Transactional;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import dedalus.domain.Location;

@Repository
@Scope( BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class LocationRepository extends ParameterRepository<Location> {
	@Override
	protected Class<Location> getEntityBean() {
		return Location.class;
	}
}