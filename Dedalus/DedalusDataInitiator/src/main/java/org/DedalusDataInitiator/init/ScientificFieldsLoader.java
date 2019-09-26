package org.DedalusDataInitiator.init;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.DedalusDataInitiator.init.domain.ScientificField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import dedalus.service.api.ScientificFieldService;

public final class ScientificFieldsLoader {
	private static final Logger logger = LoggerFactory.getLogger(ScientificFieldsLoader.class);
	
	private static List<ScientificField> scientificFieldsInitiator = new ArrayList<ScientificField>();
	
	private static List<String> scientificFieldsRaw;
	
	public static Map<Integer, dedalus.domain.ScientificField> scientificFieldsInitiatorScientificFieldDedalusMap = new HashMap<Integer, dedalus.domain.ScientificField>();
	

	public static void loadScientificFields(ScientificFieldService scientificFieldService) throws IOException {
		logger.info("loadScientificFields");
		
		File scientificFieldsFile = ResourceUtils.getFile("classpath:For Import/ScientificFields.csv");
		scientificFieldsRaw = Files.readAllLines(scientificFieldsFile.toPath());
		
		scientificFieldsRaw.forEach (
			sfr -> {
				scientificFieldsInitiator.add(buildScientificField(sfr));
			}
		);
		
		persistScientificFields(scientificFieldService);
	}
	
	private static ScientificField buildScientificField(String rawScientificField) {
		return new ScientificField(Integer.parseInt(rawScientificField.split(";")[0]), 
				Integer.parseInt(rawScientificField.split(";")[1]), 
				rawScientificField.split(";")[2]);
	}
	
	private static void persistScientificFields(ScientificFieldService scientificFieldService) {
		scientificFieldsInitiator.forEach(
			sf -> {
				dedalus.domain.ScientificField scientificField = new dedalus.domain.ScientificField();
				scientificField.setNumber(sf.getNumber());
				scientificField.setDescription(sf.getDescription());
				
				scientificField = scientificFieldService.save(scientificField);
				
				scientificFieldsInitiatorScientificFieldDedalusMap.put(sf.getId(), scientificField);
			}
		);
	}
}

