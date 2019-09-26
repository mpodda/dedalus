package dedalus.repository;

import javax.transaction.Transactional;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import dedalus.domain.Category;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class CategoryRepository extends ParameterRepository<Category> {

	@Override
	protected Class<Category> getEntityBean() {
		return Category.class;
	}

}