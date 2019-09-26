package dedalus.repository;

import javax.transaction.Transactional;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import dedalus.domain.Result;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class ResultRepository extends AbstractJpaDao<Result> {

	@Override
	protected Class<Result> getEntityBean() {
		return Result.class;
	}
	
	public Result findByToken(Long tokenId) {
		return (Result)entityManager.createQuery("select distinct result from Result result where result.token.id=:tokenId")
				.setParameter("tokenId", tokenId)
			    .getSingleResult();
	}	

}