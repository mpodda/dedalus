package org.DedalusDataInitiator2.init;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.DedalusDataInitiator2.init.constants.ColumnIndex;
import org.DedalusDataInitiator2.init.constants.Separators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dedalus.domain.Holland;
import dedalus.service.api.HollandService;

public class HollandLoader {
	private static final Logger logger = LoggerFactory.getLogger(HollandLoader.class);
	
	private static Set<Holland> hollandSet = new HashSet<Holland>();
	
	public static void loadHollands(HollandService hollandService, List<String> questionsRows) throws IOException {
		logger.info("Begin loading Hollands");
		
		questionsRows.forEach (
				questionsRow -> {
					hollandSet.add(buildFromHolland1(questionsRow));
					hollandSet.add(buildFromHolland2(questionsRow));
				}
		);
		
		hollandSet.forEach (
			holland -> {
				if (holland != null && holland.getValue()!=null)  {
					hollandService.save(holland);
				}
			}
		);
		
		
		
		logger.info("End loading Hollands");
	}
	
	public static Holland buildFromHolland1(String questionRow) {
		Holland holland = null;
		
		try {
			holland = new Holland();
			holland.setValue(new Integer(questionRow.split(Separators.ColumnSeparator.getSeparator())[ColumnIndex.Holland1.getIndex()]));
		} catch (Exception e) {
			
		}
		
		return holland;
	}

	public static Holland buildFromHolland2(String questionRow) {
		Holland holland = null;
		
		try {
			holland = new Holland();
			holland.setValue(new Integer(questionRow.split(Separators.ColumnSeparator.getSeparator())[ColumnIndex.Holland2.getIndex()]));
		} catch (Exception e) {
			
		}
		
		return holland;
	}
	
	
	
}
