package org.DedalusDataInitiator2.init;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.DedalusDataInitiator2.init.constants.ColumnIndex;
import org.DedalusDataInitiator2.init.constants.Separators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dedalus.domain.ScientificField;
import dedalus.service.api.ScientificFieldService;

public class ScientificFieldLoader {
	private static final Logger logger = LoggerFactory.getLogger(ScientificFieldLoader.class);
	
	private static Set<ScientificField> scientificFieldSet = new HashSet<ScientificField>();
	
	public static void loadScientificFields(ScientificFieldService scientificFieldService, List<String> questionsRows) {
		logger.info("Begin loading ScientificFields");
		
		questionsRows.forEach (
				questionsRow -> {
					scientificFieldSet.add(buildFromScientificField1(questionsRow));
					scientificFieldSet.add(buildFromScientificField2(questionsRow));
					scientificFieldSet.add(buildFromScientificField3(questionsRow));
				}
		);

		
		scientificFieldSet.forEach (
				scientificField -> {
					if (scientificField != null && scientificField.getNumber() != null) {
						scientificFieldService.save(scientificField);
					}
				}
		);
		
		logger.info("End loading ScientificFields");
	}
	
	
	public static ScientificField buildFromScientificField1(String questionRow) {
		ScientificField scientificField = null;
		
		try {
			scientificField = new ScientificField();
			scientificField.setNumber(new Integer(questionRow.split(Separators.ColumnSeparator.getSeparator())[ColumnIndex.ScientificField1.getIndex()]));
		} catch (Exception e) {
			
		}
		
		return scientificField;
	}
	
	public static ScientificField buildFromScientificField2(String questionRow) {
		ScientificField scientificField = null;
		
		try {
			scientificField = new ScientificField();
			scientificField.setNumber(new Integer(questionRow.split(Separators.ColumnSeparator.getSeparator())[ColumnIndex.ScientificField2.getIndex()]));
		} catch (Exception e) {
			
		}
		
		return scientificField;
	}
	
	public static ScientificField buildFromScientificField3(String questionRow) {
		ScientificField scientificField = null;
		
		try {
			scientificField = new ScientificField();
			scientificField.setNumber(new Integer(questionRow.split(Separators.ColumnSeparator.getSeparator())[ColumnIndex.ScientificField3.getIndex()]));
		} catch (Exception e) {
			
		}
		
		return scientificField;
	}	
	
}
