package dedalus.pupil.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import dedalus.domain.Answer;
import dedalus.domain.Pupil;
import dedalus.domain.Question;
import dedalus.domain.Token;
import dedalus.pupil.domain.Step;
import dedalus.service.api.AnswerService;
import dedalus.service.api.QuestionnaireService;
import dedalus.service.api.TokenService;
import dedalus.service.api.TokenStatusService;

@Service
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PupilQuestionAnswerService {
	//TODO: Read from config later
	private final int QUESTIONS_PER_PAGE = 2;
	private final int STEPS_PER_PAGE = 3;
	
	private Pupil pupil;
	private Token token;
	private List<Question> questions;
	
	private int currentStep;
	
	private TokenService tokenService;
	private QuestionnaireService questionnaireService;
	private AnswerService answerService;
	private TokenStatusService tokenStatusService;
	
	@Autowired
	public PupilQuestionAnswerService(TokenService tokenService, 
			QuestionnaireService questionnaireService,
			 AnswerService answerService,
			 TokenStatusService tokenStatusService) {
		this.tokenService = tokenService;
		this.questionnaireService = questionnaireService;
		this.answerService = answerService;
		this.tokenStatusService = tokenStatusService;
	}
	
	public void init(String tokenCode) {
		this.token = this.tokenService.byCode(tokenCode);
		this.pupil = this.token.getPupil();
		this.questions = questionnaireService.questionnaireQuestions(this.token.getQuestionnaire());
		
		
		this.currentStep = this.currentStep();
	}
	
	public String getPupilName() {
		return this.pupil.getName();
	}
	
	public String getQuestionnaireName() {
		return this.token.getQuestionnaire().getDescription();
	}
	
	public List<Step> getCurrentSteps() {
		List<Step> currentSteps = new ArrayList<>(this.stepsPerPage());
		
		List<Integer> steps = this.answerService.stepsFromNotAnsweredByToken(this.token);
		
		if (steps.size() > STEPS_PER_PAGE) {
			steps = steps.subList(0, STEPS_PER_PAGE);
		}
		
		if (steps != null && !steps.isEmpty()) {
			steps.forEach (
				s -> currentSteps.add(new Step(s, false))
			);
			
			currentSteps.get(0).setIsCurrent(true);
		}
		
		return currentSteps;
	}
	
	public List<Step> getPreviousSteps() {
		List<Step> previousSteps = this.getCurrentSteps();
		
		if (this.currentStep > 1) {
			this.currentStep--;
		}
		
		previousSteps.get(0).setIsCurrent(false);
		previousSteps.add(0, new Step(this.currentStep, true));
		
		previousSteps.remove(previousSteps.size()-1);
		
		return previousSteps;
	}
	
	public List<Answer> getNextQuestions() {
		return this.answerService.findByTokenAndStep(this.token, this.currentStep);
	}
	
	public List<Answer> getPreviousQuestions() {
		return this.answerService.findByTokenAndStep(this.token, this.currentStep);
	}
	
	public boolean isTokenExists() {
		return false;
	}
	
	public boolean isTokenAssigned() {
		return false;
	}
	
	public boolean isTokenAnswered() {
		return false;
	}
	
	public List<Answer> setAnswers(List<Answer> answers) throws Exception {
		if (answers == null) {
			throw new Exception("List value is null");
		}
		
		if (answers.stream().filter(a -> a.getValue() == null).findFirst().isPresent()) {
			throw new Exception("List contains empty values");
		}
		
		int answered = this.answerService.countAnsweredByToken(this.token);
		if (answered == 0) {
			this.token = this.tokenStatusService.onGoing(this.token);
			this.token = this.tokenService.save(this.token);
		}
		
		answers = this.answerService.saveAnswers(answers);
		
		int notAnswered = this.answerService.countNotAnsweredByToken(this.token);
		if (notAnswered == 0) {
			this.token = this.tokenStatusService.completed(this.token);
			this.token = this.tokenService.save(this.token);
		}
		
//		calculateResults();
		
		this.currentStep++;
		
		return answers;
	}

	//TODO: Turn to 'private' after test is over
//	public void calculateResults() {
//		System.out.println("calculateResults()");
//		
//		
//	}
	
	public double getCompletion () {
		if (this.questions != null) {
			int allQuestions = this.answerService.countByToken(this.token); //this.questions.size();
			int answered = this.answerService.countAnsweredByToken(this.token);
			
			double completion = (new Double(answered) / new Double (allQuestions)) * 100.0;
			
			DecimalFormat decimalFormat = new DecimalFormat();
			decimalFormat.setMaximumFractionDigits(2);
			
			return Double.valueOf(decimalFormat.format(completion));
		}
		
		return 0;
	}
	
	/*    
	  -------------
	  -- Helpers --
	  -------------
	 */
	 
	private int countQuestions() {
		return this.questions == null?0:this.questions.size();
	}
	
	private int questionsPerStep() {
		return QUESTIONS_PER_PAGE;
	}
	
	private int stepsPerPage() {
		return STEPS_PER_PAGE;
	}
	
	private int countAnsweredQuestions() {
		return this.answerService.countAnsweredByToken(this.token);
	}
	
	private int countNotAnsweredQuestions() {
		return this.answerService.countNotAnsweredByToken(this.token);
	}
	
	private int coutTotalSteps() {
		return (int)Math.ceil(this.countQuestions() / new Double(this.questionsPerStep()));
	}
	
	private int countNextSteps() {
		return (int)Math.ceil(this.countNotAnsweredQuestions() / new Double(this.questionsPerStep()));
	}
	
	private int currentStep() {
		List<Integer> steps = this.answerService.stepsFromNotAnsweredByToken(this.token);
		
		if (steps != null && !steps.isEmpty()) {
			return steps.get(0);
		}
		
		
		return 0; 
				
		//return (this.coutTotalSteps() - this.countNextSteps())  + 1;
	}

	private List<Integer> getNextSteps() {
		List<Integer> nextSteps = new ArrayList<>();
		
		/*
		for (int step = this.currentStep()+1; step < this.currentStep() + this.stepsPerPage(); step++){
			if (step > this.coutTotalSteps()){
				break;
			}
			
			nextSteps.add(step);
		}
		*/
		
		return nextSteps;
	} 
	
	public boolean isCompleted() {
//		System.out.println(String.format("Not answered = %s", this.countNotAnsweredQuestions()));
		return this.countNotAnsweredQuestions() == 0;
	}
	
	public Token getToken() {
		return token;
	}
	
	public static void main(String[] args) {
		/*
		int i;
		int a = 5, b = 5;
		i = (int) Math.ceil(a / new Double(b));
				//floorDiv(a, new Double(b));
		System.out.println(i);
		*/
		
		/*
		List<String> s = new ArrayList<String>();
		
		s.add("Bla");
		s.add("Blo");
		s.add("Bli");
		
		s.remove(s.size()-1);
		
		s.forEach(
				str -> System.out.println(str)
		);
		*/
		
		int allQuestions = 237;
		int answered = 1;
		
		double completion = (new Double(answered) / new Double (allQuestions)) * 100.0;
		
		DecimalFormat decimalFormat = new DecimalFormat();
		decimalFormat.setMaximumFractionDigits(2);
		
		completion = Double.valueOf(decimalFormat.format(completion));
		
		System.out.println(completion);
		
		
		
		//System.out.println(completion);
		
	}

	
}
