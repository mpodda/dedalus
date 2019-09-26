package dedalus.controller;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dedalus.domain.Question;
import dedalus.domain.Questionnaire;
import dedalus.domain.structures.EligibleQuestion;
import dedalus.service.TemporaryEntriesService;
import dedalus.service.api.QuestionnaireService;

@Controller
public class QuestionnaireController {
	private QuestionnaireService questionnaireService;
	private TemporaryEntriesService<Questionnaire, Question, EligibleQuestion> temporaryEntriesService;
	
	
	@Autowired
	public QuestionnaireController(QuestionnaireService questionnaireService, TemporaryEntriesService<Questionnaire, Question, EligibleQuestion> temporaryEntriesService) {
		this.questionnaireService = questionnaireService;
		this.temporaryEntriesService = temporaryEntriesService;
	}
	
	@RequestMapping("/app/parameters/questionnaires")
	public String questionnaires() {
		return "/app/parameters/questionnaires";
	}
	
	@RequestMapping("/data/questionnaires")
	public @ResponseBody List<Questionnaire> getQuestionnaires() {
		return this.questionnaireService.findAll();
	}
	
	@RequestMapping(value="/data/questionnaire/create", method = RequestMethod.POST)
	public @ResponseBody Questionnaire createQuestionnaire() {
		return this.questionnaireService.create();
	}
	
	@RequestMapping(value="/data/questionnaire/save", method = RequestMethod.POST)
	public @ResponseBody Questionnaire saveQuestionnaire(@RequestBody Questionnaire questionnaire) {
		return this.questionnaireService.saveWithQuestions(questionnaire);
	}
	
	@RequestMapping(value="/data/questionnaire/delete", method = RequestMethod.POST)
	public @ResponseBody Questionnaire deleteQuestionnaire(@RequestBody Questionnaire questionnaire) {
		this.questionnaireService.delete(questionnaire);
		
		return questionnaire;
	}
	
	@RequestMapping(value="/data/questionnaire/get", method = RequestMethod.POST)
	public @ResponseBody Questionnaire getQuestionnaire(@RequestBody Questionnaire questionnaire) {
		return this.questionnaireService.get(questionnaire);
	}

	/* --------------- */
	/* -- Questions -- */
	/* --------------- */
	
	@RequestMapping(value="/data/questionnaire/questions", method = RequestMethod.POST)
	public @ResponseBody List<Question> questionnaireQuestions(@RequestBody Questionnaire questionnaire) {
		this.temporaryEntriesService.init(questionnaire);
		this.temporaryEntriesService.setEntriesList(this.questionnaireService.questionnaireQuestions(questionnaire));
		
		return this.temporaryEntriesService.getEntriesList();
	}
	
	
	@RequestMapping("/data/questionnaire/questions/eligible")
	public @ResponseBody List<EligibleQuestion> questionnaireEligibleQuestions(@RequestBody Questionnaire questionnaire) {
		this.temporaryEntriesService.setEligibleEntriesList(this.questionnaireService.questionnaireEligibleQuestions(questionnaire));
		
		return this.temporaryEntriesService.getEligibleEntriesList();
	}
	
	@RequestMapping("/data/questionnaire/questions/update")
	public @ResponseBody Questionnaire updateQuestionnaireQuestions(@RequestBody List<Question> questions) {
		Questionnaire questionnaire = this.temporaryEntriesService.getEntity();
		
		this.temporaryEntriesService.init(null);
		
		return this.questionnaireService.updateQuestionnaireQuestions(questionnaire, questions);
	}

	@RequestMapping("/data/questionnaire/questions/temp/add")
	public @ResponseBody List<Question> addQuestionsTemporary(@RequestBody List<EligibleQuestion> eligibleQuestions){
		this.temporaryEntriesService.addSelectedEligibleEntriesToEntries(eligibleQuestions);
		
		return this.temporaryEntriesService.getEntriesList();
	}
	
	@RequestMapping("/data/questionnaire/questions/temp/delete")
	public @ResponseBody List<Question> deleteQuestionTemporary(@RequestBody Question question) {
		this.temporaryEntriesService.deleteEntry(question);
		
		return temporaryEntriesService.getEntriesList();
	}
	
}