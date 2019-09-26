package org.DedalusDataInitiator.init;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.DedalusDataInitiator.init.domain.Question;
import org.DedalusDataInitiator.init.domain.SameQuestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import dedalus.service.api.QuestionService;

public final class SameQuestionsLoader {
	private static final Logger logger = LoggerFactory.getLogger(SameQuestionsLoader.class);
	
	private static Set<SameQuestion> questionSameQuestionsInitiatorSet = new java.util.HashSet<SameQuestion>();
	
	private static List<String> questionSameQuestionsRaw;
	
	public static void loadSameQuestions(QuestionService questionService,
		Map<Integer, dedalus.domain.Question> questionInitiatorQuestionDedalusMap) throws IOException {
		
		logger.info("loadSameQuestions");
		
		File sameQuestionsFile = ResourceUtils.getFile("classpath:For Import/SameQuestions.csv");
		questionSameQuestionsRaw = Files.readAllLines(sameQuestionsFile.toPath());
		
		questionSameQuestionsRaw.forEach (
			qsqr -> {
				questionSameQuestionsInitiatorSet.add(buildSameQuestion(qsqr));
			}
		);
		
		try {
			persistSameQuestions(questionService, questionInitiatorQuestionDedalusMap);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private static SameQuestion buildSameQuestion(String rawSameQuestion) {
		return new SameQuestion(new Question(Integer.parseInt(rawSameQuestion.split(";")[0]), 0), 
				new Question(Integer.parseInt(rawSameQuestion.split(";")[1]), 0));
	}
	
	private static void persistSameQuestions(QuestionService questionService,
			Map<Integer, dedalus.domain.Question> questionInitiatorQuestionDedalusMap) {
		
		questionInitiatorQuestionDedalusMap.forEach(
			(rawQuestionId, dedalusQuestion) -> {
				List<dedalus.domain.Question> sameQuestions = new ArrayList<dedalus.domain.Question>();
				
				questionSameQuestionsInitiatorSet.forEach(
					qsqi -> {
						if (qsqi.getQuestion().getId() == rawQuestionId.intValue()) {
							sameQuestions.add(questionInitiatorQuestionDedalusMap.get(qsqi.getSameQuestion().getId()));
					
//							System.out.println(String.format("Question=%s Same Question=%s", qsqi.getQuestion().getId(), qsqi.getSameQuestion().getId()));
						}
					}
				);
				
				questionService.updateSameQuestions(dedalusQuestion, sameQuestions);
			}
		);
	}
}
