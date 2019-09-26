package org.DedalusDataInitiator2;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import org.DedalusDataInitiator2.init.CategoryLoader;
import org.DedalusDataInitiator2.init.HollandLoader;
import org.DedalusDataInitiator2.init.QuestionLoader;
import org.DedalusDataInitiator2.init.QuestionnaireCreator;
import org.DedalusDataInitiator2.init.SameQuestionLoader;
import org.DedalusDataInitiator2.init.ScientificFieldLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import dedalus.service.api.CategoryService;
import dedalus.service.api.HollandService;
import dedalus.service.api.QuestionService;
import dedalus.service.api.QuestionnaireService;
import dedalus.service.api.ScientificFieldService;

@ComponentScan(basePackages = {"dedalus"})
@Component
public class DataInitiatorRunner2 implements ApplicationRunner {
	private static final Logger logger = LoggerFactory.getLogger(DataInitiatorRunner2.class);
	
	private static List<String> questionsRows;
	
	private HollandService hollandService;
	private CategoryService categoryService;
	private ScientificFieldService scientificFieldService;
	private QuestionService questionService;
	private QuestionnaireService questionnaireService;
	
	@Autowired
	public DataInitiatorRunner2(HollandService hollandService, CategoryService categoryService, ScientificFieldService scientificFieldService,
			QuestionService questionService,
			QuestionnaireService questionnaireService) {
		this.hollandService = hollandService;
		this.categoryService = categoryService;
		this.scientificFieldService = scientificFieldService;
		this.questionService = questionService;
		this.questionnaireService = questionnaireService;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("Begin Run");
		
		File questionsFile = ResourceUtils.getFile("classpath:For Import/V2/Erotiseis dedalus.csv");
		
		questionsRows = Files.readAllLines(questionsFile.toPath());
		
		questionsRows = questionsRows.subList(1, questionsRows.size());
		
		HollandLoader.loadHollands(hollandService, questionsRows);
		
		CategoryLoader.loadCategories(categoryService, questionsRows);
		
		ScientificFieldLoader.loadScientificFields(scientificFieldService, questionsRows);
		
		QuestionLoader.loadQuestions(questionService, categoryService, hollandService, scientificFieldService, questionsRows);
		
		SameQuestionLoader.loadSameQuestions(questionService, questionsRows);
		
		QuestionnaireCreator.createQuestionnaire(questionnaireService, questionService);
		
		
		logger.info("End Run");
	}

}
