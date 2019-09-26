package dedalus.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import dedalus.domain.Faculty;
import dedalus.domain.Question;
import dedalus.domain.Questionnaire;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class QuestionRepository extends ParameterRepository<Question> {

	@Override
	protected Class<Question> getEntityBean() {
		return Question.class;
	}
	
	@SuppressWarnings("unchecked")
	public List<Questionnaire> getQuestionnaires(Long id){
		return entityManager.createQuery("select distinct questionnaire from Questionnaire questionnaire join questionnaire.questions questions where questions.id=:id")
				.setParameter("id", id)
			    .getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Question> getSameQuestions(Long id) {
		return entityManager.createQuery("select distinct question from  Question question join question.sameQuestions sameQuestions where question.id=:id")
				.setParameter("id", id)
			    .getResultList();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Faculty> getFaculties(Long id){
		return entityManager.createQuery("select distinct faculty from Faculty faculty join faculty.questions questions where questions.id=:id")
				.setParameter("id", id)
			    .getResultList();
	}
	
	public int countQuestionsByCategory(Long categoryId) {
		return ((Number)entityManager.createQuery("select count(question) from Question question where question.category.id = :categoryId")
				.setParameter("categoryId", categoryId)
			    .getSingleResult()).intValue();
	}
	
	public int countQuestionsByCategory2(Long categoryId) {
		return ((Number)entityManager.createQuery("select count(question) from Question question where question.category2.id = :categoryId")
				.setParameter("categoryId", categoryId)
			    .getSingleResult()).intValue();
	}
	
	public int countQuestionsBySubcategory(Long categoryId) {
		return ((Number)entityManager.createQuery("select count(question) from Question question where question.subCategory.id = :categoryId")
				.setParameter("categoryId", categoryId)
			    .getSingleResult()).intValue();
	}
	
}
