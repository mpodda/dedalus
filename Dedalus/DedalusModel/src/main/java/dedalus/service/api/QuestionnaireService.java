package dedalus.service.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dedalus.domain.Question;
import dedalus.domain.Questionnaire;
import dedalus.domain.structures.EligibleQuestion;
import dedalus.repository.AbstractJpaDao;
import dedalus.repository.ParameterRepository;
import dedalus.repository.QuestionRepository;
import dedalus.repository.QuestionnaireRepository;

@Service
public class QuestionnaireService extends ParameterService<Questionnaire> {
	private QuestionnaireRepository questionnaireRepository;
	private QuestionRepository questionRepository;
	

	@Autowired
	public QuestionnaireService(QuestionnaireRepository questionnaireRepository, QuestionRepository questionRepository) {
		this.questionnaireRepository = questionnaireRepository;
		this.questionRepository = questionRepository;
	}
	
	@Override
	public ParameterRepository<Questionnaire> getParameterRepository() {
		return this.questionnaireRepository;
	}
	
	public List<Question> questionnaireQuestions(Questionnaire questionnaire) {
		return this.questionnaireRepository.getQuestionnaireQuestions(questionnaire.getId());
	}
	
	public List<EligibleQuestion> questionnaireEligibleQuestions(Questionnaire questionnaire) {
		List<Question> allQuestions = this.questionRepository.findAll();
		
		List<EligibleQuestion> eligibleQuestions = new ArrayList<EligibleQuestion>(allQuestions.size());
		
		allQuestions.forEach (
			q -> eligibleQuestions.add(new EligibleQuestion(q, false))
		);
		
		return eligibleQuestions;
	}
	
	public Questionnaire updateQuestionnaireQuestions(Questionnaire questionnaire, List<Question> questions) {
		questionnaire = this.questionnaireRepository.findOne(questionnaire.getId());
		questionnaire.setQuestions(new HashSet<>(questions));
		
		this.questionnaireRepository.merge(questionnaire);
		
		return this.questionnaireRepository.findOne(questionnaire.getId());
	}
	
	public List<Questionnaire> findAllExcept(Questionnaire questionnaireToExclude) {
		if (questionnaireToExclude == null) {
			return this.findAll();
		}
		
		return this.findAll().stream()
				.filter( qnr-> !qnr.equals(questionnaireToExclude))
				.collect(Collectors.toList());		
	}
	
	
	public Questionnaire saveWithQuestions(Questionnaire questionnaire) {
		questionnaire.setQuestions(new HashSet<Question>(this.questionnaireQuestions(questionnaire)));
		return super.save(questionnaire);
	}

}