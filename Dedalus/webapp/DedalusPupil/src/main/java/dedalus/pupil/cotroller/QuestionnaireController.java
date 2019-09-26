package dedalus.pupil.cotroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dedalus.domain.Answer;
import dedalus.domain.structures.ResultsModel;
import dedalus.pupil.domain.Step;
import dedalus.pupil.service.PupilQuestionAnswerService;
import dedalus.pupil.service.ResultsCalculationService;
import dedalus.service.api.TokenService;

@Controller
public class QuestionnaireController {
	private PupilQuestionAnswerService questionnaireService;
	private ResultsCalculationService resultsCalculationService;
	private TokenService tokenService;
	
	
	@Autowired
	public QuestionnaireController(PupilQuestionAnswerService questionnaireService,
			ResultsCalculationService resultsCalculationService, TokenService tokenService) {
		this.questionnaireService = questionnaireService;
		this.resultsCalculationService = resultsCalculationService;
		this.tokenService = tokenService;
	}
	
	
	@RequestMapping(value="/questionnaire", method=RequestMethod.GET)
	public String loadQuestionnaire(Model model, @RequestParam("token") String tokenCode) {
//		System.out.println(String.format("Token= %s", tokenCode));
		
		questionnaireService.init(tokenCode);
		
		if (!questionnaireService.isCompleted()) {
			return "questionnaire4";
		}
		
		//questionnaireService.calculateResults();
		
		
		return "results";
	}
	//@PathVariable("id") Long id
	@RequestMapping(value="/results/{tokenCode}", method=RequestMethod.GET)
	public @ResponseBody ResultsModel getResults(@PathVariable("tokenCode") String tokenCode) {
//		System.out.println(String.format("Get results for token: %s", tokenCode));
		
		return this.resultsCalculationService.calculateResults(tokenService.byCode(tokenCode));
	}
	
	@RequestMapping(value="/token", method=RequestMethod.GET)
	public @ResponseBody String getCurrentTokenCode() {
		
		return this.questionnaireService.getToken()==null?"":this.questionnaireService.getToken().getValue();
	}	
	
	
	@RequestMapping(value="/templates", method=RequestMethod.GET)
	public String loadTemplates(Model model) {
		return "templates";
	}	

	
	@RequestMapping(value="/isCompleted", method=RequestMethod.GET)
	public @ResponseBody Boolean loadTemplates() {
		return Boolean.valueOf(this.questionnaireService.isCompleted());
	}
	
	@RequestMapping(value="/data/loaded")
	public @ResponseBody Boolean isLoaded() {
		return true;
	}
	
	@RequestMapping(value="/data/pupilName")
	public @ResponseBody String getPupilName() {
		return questionnaireService.getPupilName();
	}
	
	@RequestMapping(value="/data/questionnaire")
	public @ResponseBody String getQuestionnaireName() {
		return questionnaireService.getQuestionnaireName();
	}
	
	@RequestMapping(value="/data/steps/previous")
	public @ResponseBody List<Step> getPreviousSteps() {
		return this.questionnaireService.getPreviousSteps();
	}
	
	@RequestMapping(value="/data/steps/current")
	public @ResponseBody List<Step> getCurrentSteps() {
		return this.questionnaireService.getCurrentSteps();
	}
	
	@RequestMapping(value="/data/answers", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<List<Answer>> setAnswers(@RequestBody List<Answer> answers) {
		
		try {
			return new ResponseEntity<List<Answer>> (questionnaireService.setAnswers(answers), HttpStatus.OK);
		} catch (Exception exception) {
			return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		 
		//return questionnaireService.setAnswers(answers) ;
	}
	
	@RequestMapping(value="/data/questions/next", method=RequestMethod.GET)
	public @ResponseBody List<Answer> getQuestions() {
		return this.questionnaireService.getNextQuestions();
		
	}

	@RequestMapping(value="/data/questions/previous", method=RequestMethod.GET)
	public @ResponseBody List<Answer> getPreviousQuestions() {
		return this.questionnaireService.getPreviousQuestions();
	}
	
	@RequestMapping(value="/data/reliability")
	public @ResponseBody Integer getReliability() {
		return 100;
	}

	@RequestMapping(value="/data/completion")
	public @ResponseBody Double getCompletion() {
		return this.questionnaireService.getCompletion();
	}
}
