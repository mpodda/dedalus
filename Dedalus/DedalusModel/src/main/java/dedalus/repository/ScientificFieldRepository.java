package dedalus.repository;

import javax.transaction.Transactional;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import dedalus.domain.ScientificField;

@Repository
@Scope( BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class ScientificFieldRepository extends ParameterRepository<ScientificField> {

	@Override
	protected Class<ScientificField> getEntityBean() {
		return ScientificField.class;
	}

}
