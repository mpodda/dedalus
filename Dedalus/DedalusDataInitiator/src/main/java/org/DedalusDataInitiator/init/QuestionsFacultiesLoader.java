package org.DedalusDataInitiator.init;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.DedalusDataInitiator.init.domain.Faculty;
import org.DedalusDataInitiator.init.domain.Question;
import org.DedalusDataInitiator.init.domain.QuestionFaculty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import dedalus.service.api.QuestionService;

public final class QuestionsFacultiesLoader {
	private static final Logger logger = LoggerFactory.getLogger(QuestionsFacultiesLoader.class);
	
	private static Set<QuestionFaculty> questionFacultiesInitiatorSet = new java.util.HashSet<QuestionFaculty>();
	
	private static List<String> questionFacultiesRaw;
	
//	public static Map<Question, Set<Faculty>> dedalusQuestionsFacultiesMap = new HashMap<Question, Set<Faculty>>();
	
	
	public static void loadQuestionFaculties(QuestionService questionService,
											Map<Integer, dedalus.domain.Question> questionInitiatorQuestionDedalusMap,
											 Map<Integer, dedalus.domain.Faculty> facultyInitiatorFacultyDedalusMap) throws IOException {
		
		logger.info("loadQuestionFaculties");
		
		File questionFacultiesFile = ResourceUtils.getFile("classpath:For Import/QuestionsFaculties.csv");
		questionFacultiesRaw = Files.readAllLines(questionFacultiesFile.toPath());
		
		questionFacultiesRaw.forEach (
			qfr -> {
				questionFacultiesInitiatorSet.add(bulidQuestionFaculty(qfr));
			}
		);
		
		persistQuestionFaculties(questionService, questionInitiatorQuestionDedalusMap, facultyInitiatorFacultyDedalusMap);
	} 

	private static QuestionFaculty bulidQuestionFaculty(String rawQuestionFaculty) {
		return new QuestionFaculty(
				new Question(Integer.parseInt(rawQuestionFaculty.split(";")[0]), 
						Integer.parseInt(rawQuestionFaculty.split(";")[1])),
				
				new Faculty(Integer.parseInt(rawQuestionFaculty.split(";")[2]),
						rawQuestionFaculty.split(";")[3]
				)
		);
	}
	
	private static void persistQuestionFaculties(QuestionService questionService,
			Map<Integer, dedalus.domain.Question> questionInitiatorQuestionDedalusMap,
			Map<Integer, dedalus.domain.Faculty> facultyInitiatorFacultyDedalusMap) {
		
		
		questionInitiatorQuestionDedalusMap.forEach (
				(rawQuestionId, question) -> {
					List<dedalus.domain.Faculty> faculties = new ArrayList<dedalus.domain.Faculty>();
					
					questionFacultiesInitiatorSet.forEach(
							qfi -> {
								if (qfi.getQuestion().getId() == rawQuestionId.intValue()) {
									faculties.add(facultyInitiatorFacultyDedalusMap.get(qfi.getFaculty().getId()));
								}
							}
					);
					
					questionService.updateFacutlies(question, faculties);
				}
		);
	}
}
