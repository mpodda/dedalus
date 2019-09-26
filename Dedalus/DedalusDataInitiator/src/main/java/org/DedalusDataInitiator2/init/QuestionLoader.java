package org.DedalusDataInitiator2.init;

import java.util.List;
import java.util.Optional;

import org.DedalusDataInitiator2.init.constants.ColumnIndex;
import org.DedalusDataInitiator2.init.constants.Separators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dedalus.domain.Category;
import dedalus.domain.Holland;
import dedalus.domain.Question;
import dedalus.domain.ScientificField;
import dedalus.service.api.CategoryService;
import dedalus.service.api.HollandService;
import dedalus.service.api.QuestionService;
import dedalus.service.api.ScientificFieldService;

public class QuestionLoader {
	private static final Logger logger = LoggerFactory.getLogger(QuestionLoader.class);
	
//	private static Set<Question> questionsSet = new HashSet<Question>();
	
	private static List<Category> categories;
	private static List<Holland> hollands;
	private static List<ScientificField> scientificFields;
	
	
	public static void loadQuestions(QuestionService questionService, 
			CategoryService categoryService, 
			HollandService hollandService,
			ScientificFieldService scientificFieldService,
			List<String> questionsRows) {
		logger.info("Begin loading Questions");
		
		QuestionLoader.categories = categoryService.findAll();
		QuestionLoader.hollands = hollandService.findAll();
		QuestionLoader.scientificFields = scientificFieldService.findAll();
		
		
		questionsRows.forEach (
				questionsRow -> {
					Question question = buildFromQuestion(questionsRow);
					
					if (question != null) {
						questionService.save(question);
					}
				}
		);
		
		
		logger.info("End loading Questions");
	}
	
	private static Question buildFromQuestion(String questionRow) {
		Question question = null;
		
		try {
			question = new Question();
			
			// Description
			String wholeQuestion = questionRow.split(Separators.ColumnSeparator.getSeparator())[ColumnIndex.Question.getIndex()];
			String qNr = wholeQuestion.split("\\" + Separators.QuestionNumberToQuestionSeparator.getSeparator(), 2)[0];
			String description = wholeQuestion.split("\\" + Separators.QuestionNumberToQuestionSeparator.getSeparator(), 2)[1];
			
			question.setDescription(description);
			
			try {
				question.setNumber(Integer.valueOf(qNr));
			}catch (Exception e) {
				
			}
			
			
			// ----------------
			// -- Categories --
			// ----------------
			
			Category category1 = CategoryLoader.buildFromCategory1(questionRow);
			Category category2 = CategoryLoader.buildFromCategory2(questionRow);
			Category category3 = CategoryLoader.buildFromCategory3(questionRow);
			
			// Category 1
			if (category1 != null && category1.getDescription() != null && !category1.getDescription().trim().equals("")) {
				Optional<Category> possibleCategory = QuestionLoader.categories.stream().filter(c-> c.getDescription().equals(category1.getDescription())).findFirst();
				
				if (possibleCategory.isPresent()) {
					question.setCategory(possibleCategory.get());
				}
			}
			
			// Category 2
			if (category2 != null && category2.getDescription() != null && !category2.getDescription().trim().equals("")) {
				Optional<Category> possibleCategory = QuestionLoader.categories.stream().filter(c-> c.getDescription().equals(category2.getDescription())).findFirst();
				
				if (possibleCategory.isPresent()) {
					question.setCategory2(possibleCategory.get());
				}
			}
			
			// Category 3
			if (category3 != null && category3.getDescription() != null && !category3.getDescription().trim().equals("")) {
				Optional<Category> possibleCategory = QuestionLoader.categories.stream().filter(c-> c.getDescription().equals(category3.getDescription())).findFirst();
				
				if (possibleCategory.isPresent()) {
					question.setSubCategory(possibleCategory.get());
				}
			}
			
			
			// --------------
			// -- Hollands --
			// --------------
			
			Holland holland1 = HollandLoader.buildFromHolland1(questionRow);
			Holland holland2 = HollandLoader.buildFromHolland2(questionRow);
			
			// Holland 1
			if (holland1 != null && holland1.getValue()!=null)  {
				Optional<Holland> possibleHolland = QuestionLoader.hollands.stream().filter(h -> h.getValue().equals(holland1.getValue())).findFirst();
				
				if (possibleHolland.isPresent()) {
					question.setHolland1(possibleHolland.get());
				}
			}
			

			// Holland 2
			if (holland2 != null && holland2.getValue()!=null)  {
				Optional<Holland> possibleHolland = QuestionLoader.hollands.stream().filter(h -> h.getValue().equals(holland2.getValue())).findFirst();
				
				if (possibleHolland.isPresent()) {
					question.setHolland2(possibleHolland.get());
				}
			}
			
			
			// ----------------------
			// -- Scientific Field --
			// ----------------------
			
			ScientificField scientificField1 = ScientificFieldLoader.buildFromScientificField1(questionRow);
			ScientificField scientificField2 = ScientificFieldLoader.buildFromScientificField2(questionRow);
			ScientificField scientificField3 = ScientificFieldLoader.buildFromScientificField3(questionRow);
			
			
			// ScientificField 1
			if (scientificField1 != null && scientificField1.getNumber() != null) {
				Optional<ScientificField> possibleScientificField = QuestionLoader.scientificFields.stream().filter(sf -> sf.getNumber().equals(scientificField1.getNumber())).findFirst();
				if (possibleScientificField.isPresent()) {
					question.setScientificField1(possibleScientificField.get());
				}
			}
			
			
			// ScientificField 2
			if (scientificField2 != null && scientificField2.getNumber() != null) {
				Optional<ScientificField> possibleScientificField = QuestionLoader.scientificFields.stream().filter(sf -> sf.getNumber().equals(scientificField2.getNumber())).findFirst();
				if (possibleScientificField.isPresent()) {
					question.setScientificField2(possibleScientificField.get());
				}
			}
			
			
			// ScientificField 3
			if (scientificField3 != null && scientificField3.getNumber() != null) {
				Optional<ScientificField> possibleScientificField = QuestionLoader.scientificFields.stream().filter(sf -> sf.getNumber().equals(scientificField3.getNumber())).findFirst();
				if (possibleScientificField.isPresent()) {
					question.setScientificField3(possibleScientificField.get());
				}
			}
			
		} catch (Exception e) {
			
		}
		
		return question;
	}
	

	public static void main(String[] args) {
		String q = "212. Να διευκολύνεις τον κόσμο να εντοπίσει το υλικό που αναζητά (πχ βιβλία, άρθρα, έρευνες, εικόνες κ.α.) σε μία βιβλιοθήκη";
		
		
		String q2[] = q.split("\\" + Separators.QuestionNumberToQuestionSeparator.getSeparator(), 2);
		
		//String q3[] = q.split("\\.");
		
		
		System.out.println(q2.length);
		
		
		
		System.out.println(String.format("q1 = %s", q2[0].trim()));
		System.out.println(String.format("q2 = %s", q2[1].trim()));
		
		
	}
}
