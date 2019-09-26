package org.DedalusDataInitiator2.init;

import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dedalus.domain.Question;
import dedalus.domain.Questionnaire;
import dedalus.service.api.QuestionService;
import dedalus.service.api.QuestionnaireService;

public class QuestionnaireCreator {
	private static final Logger logger = LoggerFactory.getLogger(QuestionnaireCreator.class);
	
	public static void createQuestionnaire(QuestionnaireService questionnaireService, QuestionService questionService) {
		Questionnaire questionnaire = new Questionnaire();
		
		questionnaire.setDescription("Ερωτηματολόγιο Μαθητή [from init]");
		questionnaire.setQuestionsPerStep(5);
		
		questionnaire.setQuestions(new HashSet<Question>(questionService.findAll()));

		questionnaireService.save(questionnaire);
		
		logger.info("Questionnaire Created");
		
	} 

}
