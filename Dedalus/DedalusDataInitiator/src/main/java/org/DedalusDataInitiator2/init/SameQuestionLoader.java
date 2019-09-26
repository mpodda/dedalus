package org.DedalusDataInitiator2.init;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.DedalusDataInitiator2.init.constants.ColumnIndex;
import org.DedalusDataInitiator2.init.constants.Separators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dedalus.domain.Question;
import dedalus.service.api.QuestionService;

public class SameQuestionLoader {
	private static final Logger logger = LoggerFactory.getLogger(SameQuestionLoader.class);
	
	private static List<Question> questions;
	
	public static void loadSameQuestions(QuestionService questionService, List<String> questionsRows) {
		logger.info("Begin loading Same Questions");
		
		SameQuestionLoader.questions = questionService.findAll();
		
		questions = questions.stream().filter(q-> !q.getDescription().contains("QQQ")).collect(Collectors.toList());
		
		questionsRows.forEach (
			questionsRow -> {
				Question question = buildSameQuestions (questionsRow);
				
				if (question != null) {
					/*
					System.out.println(String.format("About to set same questions to question '%s' :", question.getDescription()));
					for (Question question2 : question.getSameQuestions()) {
						System.out.println(String.format("- [%s - %s] %s", question.getId(), question2.getId(), question2.getDescription()));
					}
					*/
					questionService.updateSameQuestions(question, new ArrayList<Question>(question.getSameQuestions()));
				}
			});
		
		logger.info("End loading Same Questions");
	}
	
	
	private static Question buildSameQuestions(String questionRow) {
		Question question = null;

		String wholeQuestion = questionRow.split(Separators.ColumnSeparator.getSeparator())[ColumnIndex.Question.getIndex()];
		String qNr = wholeQuestion.split("\\" + Separators.QuestionNumberToQuestionSeparator.getSeparator(), 2)[0];
		//String description = wholeQuestion.split("\\" + Separators.QuestionNumberToQuestionSeparator.getSeparator(), 2)[1];
		
		String wholeSameQuestions = questionRow.split(Separators.ColumnSeparator.getSeparator())[ColumnIndex.QuestionRepeat.getIndex()];
		String[] sameQuestions = null;
		
		if (wholeSameQuestions != null && !wholeSameQuestions.trim().equals("")) {
			sameQuestions = wholeSameQuestions.split(Separators.RepeatQuestionSeparator.getSeparator());
			
			if (sameQuestions.length > 0) {
				Integer questionNumber = Integer.valueOf(qNr);
				question = SameQuestionLoader.questions.stream().filter( q-> q.getNumber().equals(questionNumber)).findFirst().get();
				
				Set<Question> sameQuestionsSet = new HashSet<Question>();
				
				for (int qIndex=0; qIndex<sameQuestions.length; qIndex++) {
					Integer sameQuestionNumber = Integer.valueOf(sameQuestions[qIndex].trim());
					
					sameQuestionsSet.add(SameQuestionLoader.questions.stream().filter( q-> q.getNumber().equals(sameQuestionNumber)).findFirst().get());
				}
				
				question.setSameQuestions(sameQuestionsSet);
			}
			
		}
		
		return question;
	}

	public static void main(String[] args) {
		String q = "322. Να λειτουργείς καστάστημα με καλλωπιστικά φυτά, κηπευτικά υλικά, λιπάσματα κ.α.";
		String qNr = "32";
		
		System.out.println(q.indexOf(String.format("%s.", qNr)));
		
	}
	
}
