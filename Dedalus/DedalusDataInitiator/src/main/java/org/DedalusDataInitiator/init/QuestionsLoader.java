package org.DedalusDataInitiator.init;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.DedalusDataInitiator.init.domain.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import dedalus.domain.Questionnaire;
import dedalus.service.api.QuestionService;

public final class QuestionsLoader {
	private static final Logger logger = LoggerFactory.getLogger(QuestionnaireInitiator.class);
	
	private final static int ID = 0;
	private final static int NUMBER = 1;
	private final static int DESCRIPTION = 2;
	private final static int CATEGORY1_ID = 3;
	private final static int CATEGORY1_DESCRIPTION = 4;
	private final static int CATEGORY2_ID = 5;
	private final static int CATEGORY2_DESCRIPTION = 6;
	private final static int CATEGORY3_ID = 7;
	private final static int CATEGORY3_DESCRIPTION = 8;
	private final static int HOLLAND1 = 9;
	private final static int HOLLAND2 = 10;
	private final static int SCIFIELD1 = 11;
	private final static int SCIFIELD2 = 12;
	private final static int SCIFIELD3 = 13;
	private final static int SCIFIELD4 = 14;	
	
	
	private static Set<Question> questionsSet = new HashSet<Question>();
	private static List<String> allDataRaw;

	public static Map<Integer, dedalus.domain.Question> questionInitiatorQuestionDedalusMap = new HashMap<Integer, dedalus.domain.Question>();	
	
	public static void loadQuestions(QuestionService questionService,
										Questionnaire questionnaire,
										Map<Integer, dedalus.domain.Category> categoryInitiatorCategoryDedalusMap,
										Map<Integer, dedalus.domain.Holland> hollanndInitiatorHollandDedalusMap,
										Map<Integer, dedalus.domain.ScientificField> scientificFieldsInitiatorScientificFieldDedalusMap) throws IOException {
		
		logger.info("loadQuestions");
		
		File allDataFile = ResourceUtils.getFile("classpath:For Import/All_Data.csv");
		allDataRaw = Files.readAllLines(allDataFile.toPath());
		
		allDataRaw.remove(0);
		
		allDataRaw.forEach (
			adr -> {
				questionsSet.add(buildBaseQuestion(adr));
			}
		);
		
		persistQuestions(questionService, 
				questionnaire, 
				categoryInitiatorCategoryDedalusMap,
				hollanndInitiatorHollandDedalusMap, 
				scientificFieldsInitiatorScientificFieldDedalusMap);
	}
	
	private static Question buildBaseQuestion(String rawQuestion) {
		int id = Integer.parseInt(rawQuestion.split(";")[ID]);
		
		String description = rawQuestion.split(";")[DESCRIPTION];
		int number = Integer.parseInt(rawQuestion.split(";")[NUMBER]);
		
		int category1Id = 0;
		try {
			category1Id = Integer.parseInt(rawQuestion.split(";")[CATEGORY1_ID]);
		}catch (NumberFormatException e) {
			
		}
		
		int category2Id = 0;
		try {
			category2Id = Integer.parseInt(rawQuestion.split(";")[CATEGORY2_ID]);
		}catch (NumberFormatException e) {
			
		}
		
		int category3Id = 0;
		try {
			category3Id = Integer.parseInt(rawQuestion.split(";")[CATEGORY3_ID]);
		}catch (NumberFormatException e) {
			
		}		
		
		int holland1 = 0;
		try {
			holland1 = Integer.parseInt(rawQuestion.split(";")[HOLLAND1]);
		}catch (NumberFormatException e) {
			
		}
		
		int holland2 = 0;
		try {
			holland2 = Integer.parseInt(rawQuestion.split(";")[HOLLAND2]);
		}catch (NumberFormatException e) {
			
		}
		
		int scientificField1 = 0;
		try {
			scientificField1 = Integer.parseInt(rawQuestion.split(";")[SCIFIELD1]);
		}catch (NumberFormatException e) {
			
		}
		
		int scientificField2 = 0;
		try {
			scientificField2 = Integer.parseInt(rawQuestion.split(";")[SCIFIELD2]);
		}catch (NumberFormatException e) {
			
		}
		
		int scientificField3 = 0;
		try {
			scientificField3 = Integer.parseInt(rawQuestion.split(";")[SCIFIELD3]);
		}catch (NumberFormatException e) {
			
		}
		
		int scientificField4 = 0;
		try {
			scientificField4 = Integer.parseInt(rawQuestion.split(";")[SCIFIELD4]);
		}catch (NumberFormatException e) {
			
		}
		
		return new Question(id, description, number, category1Id, category2Id, category3Id,  holland1, holland2, scientificField1, scientificField2, scientificField3, scientificField4);
	}

	
	private static void persistQuestions(QuestionService questionService,
										Questionnaire questionnaire,
										Map<Integer, dedalus.domain.Category> categoryInitiatorCategoryDedalusMap,
										Map<Integer, dedalus.domain.Holland> hollanndInitiatorHollandDedalusMap,
										Map<Integer, dedalus.domain.ScientificField> scientificFieldsInitiatorScientificFieldDedalusMap) {

		
		questionsSet.forEach (
			qs -> {
				dedalus.domain.Question question = new dedalus.domain.Question();
				
				question.setNumber(qs.getNumber());
				
				question.setDescription(qs.getDescription());
				
				if (qs.getCategory1Id().isPresent()) {
					question.setCategory(categoryInitiatorCategoryDedalusMap.get(qs.getCategory1Id().get()));
				}
				
				if (qs.getCategory2Id().isPresent()) {
					question.setCategory2(categoryInitiatorCategoryDedalusMap.get(qs.getCategory2Id().get()));
				}
				
				if (qs.getCategory3Id().isPresent()) {
					question.setSubCategory(categoryInitiatorCategoryDedalusMap.get(qs.getCategory3Id().get()));
				}
				
				if (qs.getHolland1().isPresent()) {
					question.setHolland1(hollanndInitiatorHollandDedalusMap.get(qs.getHolland1().get()));
				}
				
				if (qs.getHolland2().isPresent()) {
					question.setHolland2(hollanndInitiatorHollandDedalusMap.get(qs.getHolland2().get()));
				}
				
				if (qs.getScientificField1().isPresent()) {
					question.setScientificField1(scientificFieldsInitiatorScientificFieldDedalusMap.get(qs.getScientificField1().get()));
				}
				
				if (qs.getScientificField2().isPresent()) {
					question.setScientificField2(scientificFieldsInitiatorScientificFieldDedalusMap.get(qs.getScientificField2().get()));
				}

				if (qs.getScientificField3().isPresent()) {
					question.setScientificField3(scientificFieldsInitiatorScientificFieldDedalusMap.get(qs.getScientificField3().get()));
				}

				if (qs.getScientificField4().isPresent()) {
					question.setScientificField4(scientificFieldsInitiatorScientificFieldDedalusMap.get(qs.getScientificField4().get()));
				}
				
				question = questionService.save(question);
				
				questionInitiatorQuestionDedalusMap.put(qs.getId(), question);
			}
		);
	}

}
