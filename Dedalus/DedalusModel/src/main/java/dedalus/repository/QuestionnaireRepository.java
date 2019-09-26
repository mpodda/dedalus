package dedalus.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import dedalus.domain.Question;
import dedalus.domain.Questionnaire;

@Repository
@Scope( BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class QuestionnaireRepository extends ParameterRepository<Questionnaire> {

	@Override
	protected Class<Questionnaire> getEntityBean() {
		return Questionnaire.class;
	}

	@SuppressWarnings("unchecked")
	public List<Question> getQuestionnaireQuestions(Long id) {
		return entityManager.createQuery("select distinct question from Question question join question.questionnaires questionnaires where questionnaires.id=:id")
				.setParameter("id", id)
			    .getResultList();
		
	}
}
