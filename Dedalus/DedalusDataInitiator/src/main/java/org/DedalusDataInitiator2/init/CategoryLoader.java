package org.DedalusDataInitiator2.init;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.DedalusDataInitiator2.init.constants.ColumnIndex;
import org.DedalusDataInitiator2.init.constants.Separators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dedalus.domain.Category;
import dedalus.service.api.CategoryService;

public class CategoryLoader {
	private static final Logger logger = LoggerFactory.getLogger(CategoryLoader.class);

	private static Set<Category> categorySet = new HashSet<Category>();
	
	public static void loadCategories(CategoryService categoryService, List<String> questionsRows) {
		logger.info("Begin loading Categories");
		
		questionsRows.forEach (
				questionsRow -> {
					categorySet.add(buildFromCategory1(questionsRow));
					categorySet.add(buildFromCategory2(questionsRow));
					categorySet.add(buildFromCategory3(questionsRow));
				}
		);
		
		
		categorySet.forEach (
			category -> {
				if (category != null && category.getDescription() != null && !category.getDescription().trim().equals("")) {
					categoryService.save(category);
				}
			}
		);
		
		logger.info("End loading Categories");
	}
	
	public static Category buildFromCategory1(String questionRow) {
		Category category = new Category();
		category.setDescription(questionRow.split(Separators.ColumnSeparator.getSeparator())[ColumnIndex.Category1.getIndex()]);
		return category;
	}
	
	public static Category buildFromCategory2(String questionRow) {
		Category category = new Category();
		category.setDescription(questionRow.split(Separators.ColumnSeparator.getSeparator())[ColumnIndex.Category2.getIndex()]);
		return category;
	}

	public static Category buildFromCategory3(String questionRow) {
		Category category = new Category();
		category.setDescription(questionRow.split(Separators.ColumnSeparator.getSeparator())[ColumnIndex.Category3.getIndex()]);
		return category;
	}

}
