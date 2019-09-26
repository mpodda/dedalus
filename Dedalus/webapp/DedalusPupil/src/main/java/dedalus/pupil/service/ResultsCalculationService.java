package dedalus.pupil.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dedalus.ApplicationStringConstants;
import dedalus.domain.Answer;
import dedalus.domain.Category;
import dedalus.domain.Holland;
import dedalus.domain.ScientificField;
import dedalus.domain.Token;
import dedalus.domain.structures.ResultSet;
import dedalus.domain.structures.ResultsModel;
import dedalus.pupil.domain.AnswerValue;
import dedalus.service.api.AnswerService;
import dedalus.service.api.CategoryService;
import dedalus.service.api.HollandService;
import dedalus.service.api.ScientificFieldService;

@Service
public class ResultsCalculationService {
	private AnswerService answerService;
	private CategoryService categoryService;
	private HollandService hollandService;
	private ScientificFieldService scientificFieldService;
	
	@Autowired
	public ResultsCalculationService(AnswerService answerService, CategoryService categoryService,
			HollandService hollandService, ScientificFieldService scientificFieldService) {
		
		this.answerService = answerService;
		this.categoryService = categoryService;
		this.hollandService = hollandService;
		this.scientificFieldService = scientificFieldService;
	}
	
	public ResultsModel calculateResults(Token token) {
		List<Answer> answers = this.answerService.findAnsweredByToken(token);
		
		//Integer sumValue = answers.stream().map(a -> a.getValue()).reduce(0, Integer::sum);
		
		ResultsModel resultsModel = new ResultsModel();
		
		resultsModel.setCategoryResultSet(this.calculateForCategory(answers));
		resultsModel.setTopCategoryForChartResultSet(extractTopFromCategories(resultsModel.getCategoryResultSet(), ApplicationStringConstants.TOP_CATEGORIES_FOR_CHART.getIntValue()));
		resultsModel.setTopCategoryForListResultSet(extractTopFromCategories(resultsModel.getCategoryResultSet(), ApplicationStringConstants.TOP_CATEGORIES_FOR_LIST.getIntValue()));
		
		resultsModel.setScientificFieldResultSet(this.calculateForScientificField(answers));
		resultsModel.setHollandResultSet(this.calculateForHolland(answers));
		
		/*
		if (sumValue > 0) {
			Integer categoriesSumValue = resultsModel.getCategoryResultSet().stream().map(rs -> rs.getValue()).reduce(0, Integer::sum);
			this.calculatePercentage(resultsModel.getCategoryResultSet(), categoriesSumValue);
			this.calculatePercentage(resultsModel.getTopCategoryForChartResultSet(), categoriesSumValue);
			this.calculatePercentage(resultsModel.getTopCategoryForListResultSet(), categoriesSumValue);
			
			Integer scientificFieldSumValue = resultsModel.getScientificFieldResultSet().stream().map(rs -> rs.getValue()).reduce(0, Integer::sum);
			this.calculatePercentage(resultsModel.getScientificFieldResultSet(), scientificFieldSumValue);
			
			Integer hollandSumValue = resultsModel.getHollandResultSet().stream().map(rs -> rs.getValue()).reduce(0, Integer::sum);
			this.calculatePercentage(resultsModel.getHollandResultSet(), hollandSumValue);
		}
		*/
		
		return resultsModel;
	}

	private static List<ResultSet<Category>> extractTopFromCategories(List<ResultSet<Category>> categoryResultSet, int topRecords) {
		if (categoryResultSet != null) {
			if (categoryResultSet.size() >= topRecords) {
				return categoryResultSet.subList(0, topRecords);
			}
			
			return categoryResultSet;
		}
		
		return null;
	}
	
	private <T> void calculatePercentage(List<ResultSet<T>> resultSet, Integer sumValue) {
		resultSet.forEach (
			rs -> {
				System.out.println(rs.toString());
				
				if (rs.getQuestionsRelatedToSubject() > 0) {
					rs.setPercentage(new Double ((100.00 * rs.getValue()) / rs.getQuestionsRelatedToSubject() /*sumValue*/));
				}
			} 
		);
	}
	
	private List<ResultSet<Category>> calculateForCategory(List<Answer> answers) {
		List<ResultSet<Category>> resultSets = new ArrayList<ResultSet<Category>>();
		
		this.categoryService.findAll().forEach (
			c -> {
				Integer questionsRelatedToCategory = answers.stream().filter (
					a -> (a.getQuestion().getCategory() != null &&  a.getQuestion().getCategory().equals(c)) ||  
						(a.getQuestion().getCategory2() != null && a.getQuestion().getCategory2().equals(c)) || 
						(a.getQuestion().getSubCategory() != null && a.getQuestion().getSubCategory().equals(c))
				).collect(Collectors.toList()).size() * AnswerValue.maxGrade().getValue();
				
				resultSets.add(new ResultSet<Category>(c, 0, new Double(0), questionsRelatedToCategory));
			}
		);
		
		answers.forEach (
			a -> {
				Category category1 = a.getQuestion().getCategory();
				Category category2 = a.getQuestion().getCategory2();
				Category category3 = a.getQuestion().getSubCategory();
				
				Integer value = a.getValue();
				
				resultSets.stream().filter(rs -> rs.getSubject().equals(category1)).findFirst().ifPresent(rs -> rs.setValue(rs.getValue() + value));
				resultSets.stream().filter(rs -> rs.getSubject().equals(category2)).findFirst().ifPresent(rs -> rs.setValue(rs.getValue() + value));
				resultSets.stream().filter(rs -> rs.getSubject().equals(category3)).findFirst().ifPresent(rs -> rs.setValue(rs.getValue() + value));
			}
		);
	
		return resultSets.stream().sorted (
				(resultSet1, resultSet2) -> new Double(resultSet2.getPercentage() * 100).intValue() - new Double(resultSet1.getPercentage() * 100).intValue()
			).collect(Collectors.toList());
	}
	
	private List<ResultSet<ScientificField>> calculateForScientificField(List<Answer> answers) {
		List<ResultSet<ScientificField>> resultSets = new ArrayList<ResultSet<ScientificField>>();
		
		this.scientificFieldService.findAll().forEach (
			sf -> {
				Integer questionsRelatedToScientificField = answers.stream().filter (
						a -> (a.getQuestion().getScientificField1() != null && a.getQuestion().getScientificField1().equals(sf)) ||
							(a.getQuestion().getScientificField2() != null && a.getQuestion().getScientificField2().equals(sf)) ||
							(a.getQuestion().getScientificField3() != null && a.getQuestion().getScientificField3().equals(sf)) ||
							(a.getQuestion().getScientificField4() != null && a.getQuestion().getScientificField4().equals(sf))
					).collect(Collectors.toList()).size() * AnswerValue.maxGrade().getValue();
				
				resultSets.add(new ResultSet<ScientificField>(sf, 0, new Double(0), questionsRelatedToScientificField));
			}
		);
		
		answers.forEach (
			a -> {
				ScientificField scientificField1 = a.getQuestion().getScientificField1();
				ScientificField scientificField2 = a.getQuestion().getScientificField2();
				ScientificField scientificField3 = a.getQuestion().getScientificField3();
				ScientificField scientificField4 = a.getQuestion().getScientificField4();
				Integer value = a.getValue();
				
				resultSets.stream().filter(rs -> rs.getSubject().equals(scientificField1)).findFirst().ifPresent(rs -> rs.setValue(rs.getValue() + value));
				resultSets.stream().filter(rs -> rs.getSubject().equals(scientificField2)).findFirst().ifPresent(rs -> rs.setValue(rs.getValue() + value));
				resultSets.stream().filter(rs -> rs.getSubject().equals(scientificField3)).findFirst().ifPresent(rs -> rs.setValue(rs.getValue() + value));
				resultSets.stream().filter(rs -> rs.getSubject().equals(scientificField4)).findFirst().ifPresent(rs -> rs.setValue(rs.getValue() + value));
			}
		);
		
		return resultSets.stream().sorted (
				(resultSet1, resultSet2) -> new Double(resultSet2.getPercentage() * 100).intValue() - new Double(resultSet1.getPercentage() * 100).intValue()
			).collect(Collectors.toList());
	}
	
	private List<ResultSet<Holland>> calculateForHolland(List<Answer> answers) {
		List<ResultSet<Holland>> resultSets = new ArrayList<ResultSet<Holland>>();
		
		//Integer categoriesSumValue = resultsModel.getCategoryResultSet().stream().map(rs -> rs.getValue()).reduce(0, Integer::sum);
		
		this.hollandService.findAll().forEach (
			h -> {
				
				Integer questionsRelatedToHolland = answers.stream().filter (
						a -> (a.getQuestion().getHolland1() != null && a.getQuestion().getHolland1().equals(h)) || 
							(a.getQuestion().getHolland2() != null && a.getQuestion().getHolland2().equals(h))
					).collect(Collectors.toList()).size() * AnswerValue.maxGrade().getValue();
				
				
				/*
				Integer questionsRelatedToHolland = answers.stream().filter (
						a -> (a.getQuestion().getHolland1() != null && a.getQuestion().getHolland1().equals(h)) || 
							(a.getQuestion().getHolland2() != null && a.getQuestion().getHolland2().equals(h))
					).collect(Collectors.toList()).stream().map(a -> a.getValue()).reduce(0, Integer::sum);
				*/
				
				resultSets.add(new ResultSet<Holland>(h, 0, new Double(0), questionsRelatedToHolland));
			}
		);
		
		answers.forEach (
			a -> {
				Holland holland1 = a.getQuestion().getHolland1();
				Holland holland2 = a.getQuestion().getHolland2();
				
				Integer value = a.getValue();
				
				resultSets.stream().filter(rs -> rs.getSubject().equals(holland1)).findFirst().ifPresent(rs -> rs.setValue(rs.getValue() + value));
				resultSets.stream().filter(rs -> rs.getSubject().equals(holland2)).findFirst().ifPresent(rs -> rs.setValue(rs.getValue() + value));
			}
		);
		
		return resultSets.stream().sorted (
					(resultSet1, resultSet2) -> new Double(resultSet2.getPercentage() * 100).intValue() - new Double(resultSet1.getPercentage() * 100).intValue()
				).collect(Collectors.toList());
	}
	
	public static void main(String[] args) {
		Double d = new Double(12.59);
		
		System.out.println(new Double(d * 100).intValue());
		
	}
}