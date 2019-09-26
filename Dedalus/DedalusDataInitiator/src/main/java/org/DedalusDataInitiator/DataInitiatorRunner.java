package org.DedalusDataInitiator;

import java.util.ArrayList;

import org.DedalusDataInitiator.init.CategoriesLoader;
import org.DedalusDataInitiator.init.FacultiesLoader;
import org.DedalusDataInitiator.init.FacultiesLocationsLoader;
import org.DedalusDataInitiator.init.HollandLoader;
import org.DedalusDataInitiator.init.LocationsLoader;
import org.DedalusDataInitiator.init.QuestionnaireInitiator;
import org.DedalusDataInitiator.init.QuestionsLoader;
import org.DedalusDataInitiator.init.ScientificFieldsLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import dedalus.domain.Question;
import dedalus.service.api.CategoryService;
import dedalus.service.api.FacultyService;
import dedalus.service.api.HollandService;
import dedalus.service.api.LocationService;
import dedalus.service.api.QuestionService;
import dedalus.service.api.QuestionnaireService;
import dedalus.service.api.ScientificFieldService;

//@SpringBootApplication (scanBasePackages = {"dedalus"})

@ComponentScan(basePackages = {"dedalus"})
@Component
public class DataInitiatorRunner implements ApplicationRunner {
	private static final Logger logger = LoggerFactory.getLogger(DataInitiatorRunner.class);
	
	private CategoryService categoryService;
	private FacultyService facultyService;
	private HollandService hollandService;
	private QuestionnaireService questionnaireService;
	private QuestionService questionService;
	private ScientificFieldService scientificFieldService;
	private LocationService locationService;
	
	
	@Autowired
	public DataInitiatorRunner(CategoryService categoryService, FacultyService facultyService,
			HollandService hollandService, QuestionnaireService questionnaireService, QuestionService questionService,
			ScientificFieldService scientificFieldService, LocationService locationService) {
		
		this.categoryService = categoryService;
		this.facultyService = facultyService;
		this.hollandService = hollandService;
		this.questionnaireService = questionnaireService;
		this.questionService = questionService;
		this.scientificFieldService = scientificFieldService;
		this.locationService = locationService;
	}

	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("Start Data Initiation");
		
		
		CategoriesLoader.loadCategories(categoryService);
		FacultiesLoader.loadFaculties(facultyService);
		LocationsLoader.loadLocations(locationService);
		HollandLoader.loadHollands(hollandService);
		ScientificFieldsLoader.loadScientificFields(scientificFieldService);
		QuestionnaireInitiator.initQuestionnaire(questionnaireService);
		
		
		QuestionsLoader.loadQuestions(questionService, 
				QuestionnaireInitiator.questionnaire, 
				CategoriesLoader.categoryInitiatorCategoryDedalusMap, 
				HollandLoader.hollanndInitiatorHollandDedalusMap, 
				ScientificFieldsLoader.scientificFieldsInitiatorScientificFieldDedalusMap);

		
		
		FacultiesLocationsLoader.loadFacultyLocations(locationService, 
				facultyService, 
				FacultiesLoader.facultyInitiatorFacultyDedalusMap, 
				LocationsLoader.locationInitiatorLocationDedalusMap);
		
		/*
		QuestionsFacultiesLoader.loadQuestionFaculties(questionService, 
				QuestionsLoader.questionInitiatorQuestionDedalusMap, 
				FacultiesLoader.facultyInitiatorFacultyDedalusMap);
		
		*/
		//SameQuestionsLoader.loadSameQuestions(questionService, QuestionsLoader.questionInitiatorQuestionDedalusMap);
		
		
		QuestionnaireInitiator.setQuestions(questionnaireService, questionService,
				new ArrayList<Question> (QuestionsLoader.questionInitiatorQuestionDedalusMap.values()));
		
		
		
		logger.info("Finish Data Initiation");
	}

}
