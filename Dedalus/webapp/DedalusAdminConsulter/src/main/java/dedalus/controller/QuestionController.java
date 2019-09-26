package dedalus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dedalus.domain.Category;
import dedalus.domain.Faculty;
import dedalus.domain.Question;
import dedalus.domain.Questionnaire;
import dedalus.domain.structures.EligibleFaculty;
import dedalus.domain.structures.EligibleQuestion;
import dedalus.domain.structures.EligibleQuestionnaire;
import dedalus.service.TemporaryEntityService;
import dedalus.service.TemporaryEntriesService;
import dedalus.service.api.CategoryService;
import dedalus.service.api.QuestionService;

@Controller
public class QuestionController {
	private QuestionService questionService;
	private CategoryService categoryService;
	private TemporaryEntityService<Question> temporaryEntityService;
	private TemporaryEntriesService<Question, Question, EligibleQuestion> temporaryEntriesServiceForSameQuestions;
	private TemporaryEntriesService<Question, Questionnaire, EligibleQuestionnaire> temporaryEntriesServiceForQuestionnaires;
	private TemporaryEntriesService<Question, Faculty, EligibleFaculty> temporaryEntriesServiceForFaculties;
	
	@Autowired
	public QuestionController(QuestionService questionService, CategoryService categoryService, 
			TemporaryEntityService<Question> temporaryEntityService,
			TemporaryEntriesService<Question, Question, EligibleQuestion> temporaryEntriesServiceForSameQuestions,
			TemporaryEntriesService<Question, Questionnaire, EligibleQuestionnaire> temporaryEntriesServiceForQuestionnaires,
			TemporaryEntriesService<Question, Faculty, EligibleFaculty> temporaryEntriesServiceForFaculties) {
		this.questionService = questionService;
		this.categoryService = categoryService;
		this.temporaryEntityService = temporaryEntityService;
		this.temporaryEntriesServiceForSameQuestions = temporaryEntriesServiceForSameQuestions;
		this.temporaryEntriesServiceForQuestionnaires = temporaryEntriesServiceForQuestionnaires;
		this.temporaryEntriesServiceForFaculties = temporaryEntriesServiceForFaculties;
	}
	
	@RequestMapping("/app/parameters/questions")
	public String questions() {
		return "/app/parameters/questions";
	}
	
	@RequestMapping("/data/questions")
	public @ResponseBody List<Question> getQuestions() {
		return this.questionService.findAll();
	}
	
	@RequestMapping(value="/data/question/create", method = RequestMethod.POST)
	public @ResponseBody Question createQuestion() {
		return this.questionService.create();
	}
	
	@RequestMapping(value="/data/question/save", method = RequestMethod.POST)
	public @ResponseBody Question saveQuestion(@RequestBody Question question) {
		return this.questionService.save(question);
	}
	
	@RequestMapping(value="/data/question/get", method = RequestMethod.POST)
	public @ResponseBody Question getQuestion(@RequestBody Question question) {
		return this.temporaryEntityService.init(this.questionService.get(question)).getEntity();
	}
	
	@RequestMapping(value="data/question/delete", method = RequestMethod.POST)
	public @ResponseBody Question deleteCategory(@RequestBody Question question) {
		this.questionService.delete(question);
		
		return question;
	}	
	
	@RequestMapping(value="/data/question/get/{id}", method = RequestMethod.GET)
	public @ResponseBody Question loadQuestion(@PathVariable("id") Long id) {
		return this.questionService.findOne(id);
	}
	
	/*  
	 ----------------
	 -- Categories --
	 ----------------  
	*/
	
	@RequestMapping("/data/questions/categories/eligible")
	public @ResponseBody List<Category> questionEligibleCategories(@RequestBody Question question) {
		this.temporaryEntityService.setEntity(question);
		
		return this.categoryService.findAllExcept(question.getCategory(), question.getCategory2(), question.getSubCategory());
	}

	@RequestMapping(value="/data/question/set/category", method = RequestMethod.POST)
	public @ResponseBody Question setCategory(@RequestBody Category category) {
		Question question = this.temporaryEntityService.getEntity();
		question.setCategory(category);
		
		return this.temporaryEntityService.setEntity(question).getEntity();
	}
	
	@RequestMapping(value="/data/question/set/category2", method = RequestMethod.POST)
	public @ResponseBody Question setCategory2(@RequestBody Category category) {
		Question question = this.temporaryEntityService.getEntity();
		question.setCategory2(category);
		
		return this.temporaryEntityService.setEntity(question).getEntity();
	}
	
	@RequestMapping(value="/data/question/set/subCategory", method = RequestMethod.POST)
	public @ResponseBody Question setSubCategory(@RequestBody Category category) {
		Question question = this.temporaryEntityService.getEntity();
		question.setSubCategory(category);
		
		return this.temporaryEntityService.setEntity(question).getEntity();
	}
	
	@RequestMapping(value="/data/question/remove/category", method = RequestMethod.POST)
	public @ResponseBody Question removeCategory() {
		Question question = this.temporaryEntityService.getEntity();
		question.setCategory(null);
		
		return this.temporaryEntityService.setEntity(question).getEntity();
	}
	
	@RequestMapping(value="/data/question/remove/category2", method = RequestMethod.POST)
	public @ResponseBody Question removeCategory2() {
		Question question = this.temporaryEntityService.getEntity();
		question.setCategory2(null);
		
		return this.temporaryEntityService.setEntity(question).getEntity();
	}
	
	@RequestMapping(value="/data/question/remove/subCategory", method = RequestMethod.POST)
	public @ResponseBody Question removeSubCategory() {
		Question question = this.temporaryEntityService.getEntity();
		question.setSubCategory(null);
		
		return this.temporaryEntityService.setEntity(question).getEntity();
	}

	
	/*  
	 --------------------
	 -- Same Questions --
	 --------------------
	*/
	
	@RequestMapping(value="/data/question/sameQuestions", method = RequestMethod.POST)
	public @ResponseBody List<Question> sameQuestions(@RequestBody Question question) {
		this.temporaryEntriesServiceForSameQuestions.init(question);
		this.temporaryEntriesServiceForSameQuestions.setEntriesList(this.questionService.sameQuestions(question));
		
		return this.temporaryEntriesServiceForSameQuestions.getEntriesList();
	}
	
	@RequestMapping("/data/question/sameQuestions/eligible")
	public @ResponseBody List<EligibleQuestion> questionEligibleSameQuestions(@RequestBody Question question) {
		this.temporaryEntriesServiceForSameQuestions.setEligibleEntriesList(this.questionService.eligibileSameQuestions());
		
		return this.temporaryEntriesServiceForSameQuestions.getEligibleEntriesList();
	}
	
	@RequestMapping("/data/question/sameQuestions/update")
	public @ResponseBody Question updateQuestionSameQuestions(@RequestBody List<Question> sameQuestions) {
		Question question = this.temporaryEntriesServiceForSameQuestions.getEntity();
		
		this.temporaryEntriesServiceForSameQuestions.init(null);
		
		return this.questionService.updateSameQuestions(question, sameQuestions);
	}
	
	@RequestMapping("/data/question/sameQuestions/temp/add")
	public @ResponseBody List<Question> addSameQuestionsTemporary(@RequestBody List<EligibleQuestion> eligibleSameQuestions){
		this.temporaryEntriesServiceForSameQuestions.addSelectedEligibleEntriesToEntries(eligibleSameQuestions);
		
		return this.temporaryEntriesServiceForSameQuestions.getEntriesList();
	}
	
	@RequestMapping("/data/question/sameQuestions/temp/delete")
	public @ResponseBody List<Question> deleteSameQuestionTemporary(@RequestBody Question sameQuestion) {
		this.temporaryEntriesServiceForSameQuestions.deleteEntry(sameQuestion);
		
		return temporaryEntriesServiceForSameQuestions.getEntriesList();
	}	
	
	/*  
	 --------------------
	 -- Questionnaires --
	 --------------------
	*/	
	
	@RequestMapping(value="/data/question/questionnaires", method = RequestMethod.POST)
	public @ResponseBody List<Questionnaire> questionnaires(@RequestBody Question question) {
		this.temporaryEntriesServiceForQuestionnaires.init(question);
		this.temporaryEntriesServiceForQuestionnaires.setEntriesList(this.questionService.questionnaires(question));
		
		return this.temporaryEntriesServiceForQuestionnaires.getEntriesList();
	}
	
	@RequestMapping("/data/question/questionnaires/eligible")
	public @ResponseBody List<EligibleQuestionnaire> questionEligibleQuestionnaires(@RequestBody Question question) {
		this.temporaryEntriesServiceForQuestionnaires.setEligibleEntriesList(this.questionService.eligibleQuestionnaires());
		
		return this.temporaryEntriesServiceForQuestionnaires.getEligibleEntriesList();
	}

	@RequestMapping("/data/question/questionnaires/update")
	public @ResponseBody Question updateQuestionQuestionnaires(@RequestBody List<Questionnaire> questionnaires) {
		Question question = this.temporaryEntriesServiceForQuestionnaires.getEntity();
		
		this.temporaryEntriesServiceForQuestionnaires.init(null);
		
		return this.questionService.updateQuestionnaires(question, questionnaires);
	}
	
	@RequestMapping("/data/question/questionnaires/temp/add")
	public @ResponseBody List<Questionnaire> addQuestionnairesTemporary(@RequestBody List<EligibleQuestionnaire> eligibleQuestionnaires){
		this.temporaryEntriesServiceForQuestionnaires.addSelectedEligibleEntriesToEntries(eligibleQuestionnaires);
		
		return this.temporaryEntriesServiceForQuestionnaires.getEntriesList();
	}
	
	@RequestMapping("/data/question/questionnaires/temp/delete")
	public @ResponseBody List<Questionnaire> deleteQuestionnaireTemporary(@RequestBody Questionnaire questionnaire) {
		this.temporaryEntriesServiceForQuestionnaires.deleteEntry(questionnaire);
		
		return temporaryEntriesServiceForQuestionnaires.getEntriesList();
	}	
	
	
	/*  
	 ---------------
	 -- Faculties --
	 ---------------
	*/	
	@RequestMapping(value="/data/question/faculties", method = RequestMethod.POST)
	public @ResponseBody List<Faculty> faculties(@RequestBody Question question) {
		this.temporaryEntriesServiceForFaculties.init(question);
		this.temporaryEntriesServiceForFaculties.setEntriesList(this.questionService.faculties(question));
		
		return this.temporaryEntriesServiceForFaculties.getEntriesList();
	}
	
	@RequestMapping("/data/question/faculties/eligible")
	public @ResponseBody List<EligibleFaculty> questionEligibleFaculties(@RequestBody Question question) {
		this.temporaryEntriesServiceForFaculties.setEligibleEntriesList(this.questionService.eligibleFaculties());
		
		return this.temporaryEntriesServiceForFaculties.getEligibleEntriesList();
	}

	@RequestMapping("/data/question/faculties/update")
	public @ResponseBody Question updateQuestionFaculties(@RequestBody List<Faculty> faculties) {
		Question question = this.temporaryEntriesServiceForFaculties.getEntity();
		
		this.temporaryEntriesServiceForFaculties.init(null);
		
		return this.questionService.updateFacutlies(question, faculties);
	}
	
	@RequestMapping("/data/question/faculties/temp/add")
	public @ResponseBody List<Faculty> addFacultiesTemporary(@RequestBody List<EligibleFaculty> eligibleFaculties){
		this.temporaryEntriesServiceForFaculties.addSelectedEligibleEntriesToEntries(eligibleFaculties);
		
		return this.temporaryEntriesServiceForFaculties.getEntriesList();
	}
	
	@RequestMapping("/data/question/faculties/temp/delete")
	public @ResponseBody List<Faculty> deleteFacultyTemporary(@RequestBody Faculty faculty) {
		this.temporaryEntriesServiceForFaculties.deleteEntry(faculty);
		
		return temporaryEntriesServiceForFaculties.getEntriesList();
	}	
	
}
