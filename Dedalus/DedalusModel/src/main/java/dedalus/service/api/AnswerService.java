package dedalus.service.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dedalus.domain.Answer;
import dedalus.domain.Question;
import dedalus.domain.Questionnaire;
import dedalus.domain.Token;
import dedalus.repository.AbstractJpaDao;
import dedalus.repository.AnswerRepository;
import dedalus.util.RandomNumberGenerator;

@Service
public class AnswerService extends IdentifiableEntityService<Answer> {
	private AnswerRepository answerRepository;
	private QuestionnaireService questionnaireService;
	
	@Autowired
	public AnswerService(AnswerRepository answerRepository,
			QuestionnaireService questionnaireService) {
		this.answerRepository = answerRepository;
		this.questionnaireService = questionnaireService;
	}
	
	@Override
	public AbstractJpaDao<Answer> getRepository() {
		return this.answerRepository;
	}
	
	public void prepareAnswersForToken(Token token) {
		if (token.getQuestionnaire()!= null) {
			Questionnaire questionnaire = questionnaireService.findOne(token.getQuestionnaire().getId());
			
			List<Question> questions = questionnaireService.questionnaireQuestions(questionnaire);
			
			List<Question> shuffleQuestions = shuffleQuestions(questions);
			
			AtomicInteger currentAnswersSize = new AtomicInteger(0);
			
			shuffleQuestions.forEach (
				question -> {
					final int currentIndex = currentAnswersSize.incrementAndGet();
					
					Answer answer = new Answer();
					answer.setToken(token);
					answer.setQuestion(question);
					answer.setStep(calculateStep(currentIndex, questionnaire.getQuestionsPerStep()));
					answer.setNumber(currentIndex);
					
					answerRepository.save(answer);
				}
			);
		}
	}
	
	private List<Question> shuffleQuestions(List<Question> questions) {
		List<Question> shuffledQuestions = new ArrayList<>(questions.size());
		
		List<Integer> shuffledPositions = new ArrayList<>(questions.size());
		
		questions.forEach (
			q-> {
				int pos = RandomNumberGenerator.generateRandomNumber (1, questions.size());
				
				while (shuffledPositions.contains(new Integer(pos-1))) {
					pos = RandomNumberGenerator.generateRandomNumber (1, questions.size());
				}
				
				shuffledPositions.add(new Integer(pos-1));
			}
		);
		
		shuffledPositions.forEach (
			pos -> shuffledQuestions.add(questions.get(pos.intValue()))
		);
		
		return shuffledQuestions;
	}

	private static int calculateStep(int currentSize, int questionsPerStep) {
		
		if (questionsPerStep != 0)  {
			return (int) Math.ceil(currentSize / new Double(questionsPerStep));
		}
		
		return 0;
	}
	
	
	
	public void resetAnswersForToken(Token token) {
		
	}
	
	public List<Answer> findNotAnsweredByToken(Token token) {
		return this.answerRepository.findNotAnsweredByToken(token.getId());
	}
	
	public List<Answer> findAnsweredByToken(Token token) {
		return this.answerRepository.findAnsweredByToken(token.getId());
	}	

	public List<Answer> findByTokenAndStep(Token token, Integer step) {
		return this.answerRepository.findByTokenAndStep(token.getId(), step);
	}
	
	public List <Integer> stepsFromNotAnsweredByToken(Token token) {
		return this.answerRepository.stepsFromNotAnsweredByToken(token.getId());	
	}
	
	public int countAnsweredByToken(Token token) {
		return this.answerRepository.findAnsweredByToken(token.getId()).size();
	}
	
	public int countNotAnsweredByToken(Token token) {
		return this.answerRepository.findNotAnsweredByToken(token.getId()).size();
	}
	
	public int countByToken(Token token) {
		return this.answerRepository.findByToken(token.getId()).size();
	}
	
	public List<Answer> saveAnswers(List<Answer> answers) {
		answers.forEach (
			a -> a  = this.answerRepository.merge(a)
		);
		
		return answers;
	}
	
	
	
	public static void main(String[] args) {
		/*
		List<Question> questions = new ArrayList();
		List<Answer> answers = new ArrayList();
		
		final int qpstep = 2;

		for (int q=1; q < 12; q++) {
			Question question = new Question();
			//question.setNumber(1);
			question.setId(new Long(q));
			question.setDescription(String.format("Question %s", q));
			
			questions.add(question);
		}

		//int currentStep = 1;
		AtomicInteger currentStep = new AtomicInteger(1);
		AtomicInteger size = new AtomicInteger(0); 
		
		questions.forEach (
			q -> {
				
				Answer answer = new Answer();
				answer.setQuestion(q);
				answer.setStep((int) Math.ceil(size.incrementAndGet() / new Double(qpstep)));
				
				//answer.setValue((int) Math.ceil(size.incrementAndGet() / new Double(qpstep)));
				answers.add(answer);
			}
		);
		
		answers.forEach (
			a -> System.out.println(String.format("Question: %s  Step = %s  value = %s", a.getQuestion().getDescription(), a.getStep(), a.getValue()))
		);
		*/
		
		/*
		Set<String> namesSet = new HashSet<String>();
		
		System.out.println(namesSet.add("Marcello"));
		System.out.println(namesSet.add("Jim"));
		System.out.println(namesSet.add("John"));
		System.out.println(namesSet.add("Marcello"));
		*/
		
		
		Set<Integer> positionsSet = new HashSet<Integer>();
		
		
		/*
		positionsSet.add(new Integer(100));
		positionsSet.add(new Integer(3));
		*/
		
		List<Integer> positionsList = new ArrayList<Integer>();

		/*
		positionsList.add(new Integer(100));
		positionsList.add(new Integer(3));
		positionsList.add(new Integer(100));
		*/

		
		
		
		/*
		for (int i=0; i<100; i++) {
			int pos = getRandom(1, 344);
			
			while (positionsList.contains(new Integer(pos))) {
				pos = getRandom(1, 344);
			}
			
			positionsList.add(new Integer(pos));
			
			//while (!positionsList.add(getRandom(1, 100)));
		}
		
		
		AtomicInteger current = new AtomicInteger(1);
		
		positionsList.forEach (
			pos ->  {
				System.out.println(pos);
				if (current.getAndIncrement() % 5 == 0) {
					System.out.println("--------------------------------");
				}
			}
		);
		*/
		
		
	}
}