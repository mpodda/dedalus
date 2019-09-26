package dedalus.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import dedalus.domain.Answer;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class AnswerRepository extends AbstractJpaDao<Answer> {

	@Override
	protected Class<Answer> getEntityBean() {
		return Answer.class;
	}

	@SuppressWarnings("unchecked")
	public List<Answer> findNotAnsweredByToken(Long tokenId) {
		return entityManager.createQuery("select distinct answer from Answer answer where answer.token.id=:id and answer.value is null order by step")
				.setParameter("id", tokenId)
			    .getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Answer> findAnsweredByToken(Long tokenId) {
		return entityManager.createQuery("select distinct answer from Answer answer where answer.token.id=:id and answer.value is not null")
				.setParameter("id", tokenId)
			    .getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Answer> findByTokenAndStep(Long tokenId, Integer step) {
		return entityManager.createQuery("select distinct answer from Answer answer where answer.token.id=:id and answer.step =:step")
				.setParameter("id", tokenId)
				.setParameter("step", step)
			    .getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List <Integer> stepsFromNotAnsweredByToken(Long tokenId) {
		return entityManager.createQuery("select distinct answer.step from Answer answer where answer.token.id=:id and answer.value is null order by answer.step")
				.setParameter("id", tokenId)
			    .getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Answer> findByToken(Long tokenId) {
		return entityManager.createQuery("select distinct answer from Answer answer where answer.token.id=:id")
				.setParameter("id", tokenId)
			    .getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Answer> findByCategoryAndToken(Long categoryId) {
		return entityManager.createQuery("select distinct answer from Answer answer where answer.question.category.id=:categoryId")
				.setParameter("categoryId", categoryId)
			    .getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Answer> findByCategory2AndToken(Long categoryId) {
		return entityManager.createQuery("select distinct answer from Answer answer where answer.question.category2.id=:categoryId")
				.setParameter("categoryId", categoryId)
			    .getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Answer> findBySubcategoryAndTOken(Long categoryId) {
		return entityManager.createQuery("select distinct answer from Answer answer where answer.question.subCategory.id=:categoryId")
				.setParameter("categoryId", categoryId)
			    .getResultList();
	}	
}
