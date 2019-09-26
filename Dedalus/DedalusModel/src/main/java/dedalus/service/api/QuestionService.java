package dedalus.service.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dedalus.domain.Category;
import dedalus.domain.Faculty;
import dedalus.domain.Question;
import dedalus.domain.Questionnaire;
import dedalus.domain.structures.EligibleFaculty;
import dedalus.domain.structures.EligibleQuestion;
import dedalus.domain.structures.EligibleQuestionnaire;
import dedalus.repository.FacultyRepository;
import dedalus.repository.ParameterRepository;
import dedalus.repository.QuestionRepository;
import dedalus.repository.QuestionnaireRepository;

@Service
public class QuestionService extends ParameterService<Question> {
	private QuestionRepository questionRepository; 
	private QuestionnaireRepository questionnaireRepository;
	private FacultyRepository facultyRepository;
	
	
	@Autowired
	public QuestionService(QuestionRepository questionRepository, QuestionnaireRepository questionnaireRepository, FacultyRepository facultyRepository) {
		this.questionRepository = questionRepository;
		this.questionnaireRepository = questionnaireRepository;
		this.facultyRepository = facultyRepository;
	}
	
	@Override
	public ParameterRepository<Question> getParameterRepository() {
		return this.questionRepository;
	}
	
	/*
	  --------------------
	  -- Same Questions --
	  --------------------
	 */
	public List<Question> sameQuestions(Question question) {
		return this.questionRepository.getSameQuestions(question.getId());
	}
	
	public List<EligibleQuestion> eligibileSameQuestions() {
		List<Question> allQuestions = this.questionRepository.findAll();
		
		List<EligibleQuestion> eligibleQuestions = new ArrayList<>(allQuestions.size());
		allQuestions.forEach (
			q -> {
				eligibleQuestions.add(new EligibleQuestion(q, false));
			}
		);
		
		return eligibleQuestions;
	}
	
	public Question updateSameQuestions(Question  question, List<Question> sameQuestions) {
		question = this.questionRepository.findOne(question.getId());
		question.setSameQuestions(new HashSet<>(sameQuestions));
		this.questionRepository.merge(question);
		return this.questionRepository.findOne(question.getId());
	}
	

	/*
	  --------------------
	  -- Questionnaires --
	  --------------------
	 */
	
	public List<Questionnaire> questionnaires(Question question) {
		return this.questionRepository.getQuestionnaires(question.getId());
	}
	
	public List<EligibleQuestionnaire> eligibleQuestionnaires() {
		List<Questionnaire> allQuestionnaires = this.questionnaireRepository.findAll();
		
		List<EligibleQuestionnaire> eligibleQuestionnaires = new ArrayList<>(allQuestionnaires.size());
		
		allQuestionnaires.forEach (
			q -> {
				eligibleQuestionnaires.add(new EligibleQuestionnaire(q));
			}
		);
		
		
		return eligibleQuestionnaires;
	}
	
	public Question updateQuestionnaires(Question question, List<Questionnaire> questionnaires) {
		question = this.questionRepository.findOne(question.getId());
		
		question.setQuestionnaires(new HashSet<>(questionnaires));
		
		this.questionRepository.merge(question);
		return this.questionRepository.findOne(question.getId());
	}
	
	/*
	  ---------------
	  -- Faculties --
	  ---------------
	 */
	
	public List<Faculty> faculties(Question question) {
		return this.questionRepository.getFaculties(question.getId());
	}
	
	public List<EligibleFaculty> eligibleFaculties(){
		List<Faculty> allFaculties = this.facultyRepository.findAll();
		
		List<EligibleFaculty> eligibleFaculties = new ArrayList<>(allFaculties.size());
		
		allFaculties.forEach (
			f -> {
				eligibleFaculties.add(new EligibleFaculty(f));
			}
		);
		
		return eligibleFaculties;
	}
	
	public Question updateFacutlies(Question question, List<Faculty> faculties) {
		question = this.questionRepository.findOne(question.getId());
		
		question.setFaculties(new HashSet<>(faculties));
		
		this.questionRepository.merge(question);
		return this.questionRepository.findOne(question.getId());
	}
	
	
	
	/*
	  ----------------
	  -- Categories --
	  ----------------
	 */
	
	public int countQuestionsByCategory(Category category) {
		return this.questionRepository.countQuestionsByCategory(category.getId());
	}
	
	public int countQuestionsByCategory2(Category category) {
		return this.questionRepository.countQuestionsByCategory2(category.getId());
	}
	
	public int countQuestionsBySubcategory(Category category) {
		return this.questionRepository.countQuestionsBySubcategory(category.getId());
	}
	
	
}