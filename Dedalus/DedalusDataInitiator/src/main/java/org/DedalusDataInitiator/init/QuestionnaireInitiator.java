package org.DedalusDataInitiator.init;

import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dedalus.domain.Question;
import dedalus.domain.Questionnaire;
import dedalus.service.api.QuestionService;
import dedalus.service.api.QuestionnaireService;

public final class QuestionnaireInitiator {
	private static final Logger logger = LoggerFactory.getLogger(QuestionnaireInitiator.class);
	
	public static Questionnaire questionnaire;
	
	public static void initQuestionnaire(QuestionnaireService questionnaireService) {
		logger.info("initQuestionnaire");
		
		questionnaire = new Questionnaire();
		questionnaire.setDescription("Ερωτηματολόγιο Μαθητή");
		
		questionnaire = questionnaireService.saveWithQuestions(questionnaire);
	}
	
	public static void setQuestions(QuestionnaireService questionnaireService, QuestionService questionService, List<Question> questions) {
		/*
		questions.forEach (
			question -> { 
				StringBuilder builder = new StringBuilder();
				
				builder.append(question.getNumber()).append(": ").append("Before: ");
				
				question = questionService.get(question);
				
				builder.append(question.getVersion()).append(" After: ").append(question.getVersion());
				
				System.out.println(builder);
			}
			
		);
		*/
		
		//questionnaireService.updateQuestionnaireQuestions(questionnaire, questions);
		
		//questionnaire.setQuestions(new HashSet<>(questions));
	
		
		
		questionnaire = questionnaireService.get(questionnaire);
		questionnaire.setQuestions(new HashSet<>(questions));
		questionnaire = questionnaireService.save(questionnaire);
	}
}