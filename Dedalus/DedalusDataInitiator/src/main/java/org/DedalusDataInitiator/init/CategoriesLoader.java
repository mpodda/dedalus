package org.DedalusDataInitiator.init;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.DedalusDataInitiator.init.domain.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import dedalus.service.api.CategoryService;

public final class CategoriesLoader {
	private static final Logger logger = LoggerFactory.getLogger(CategoriesLoader.class);
	
	private static List<Category> categoriesInitiator = new ArrayList<Category>();
	private static List<String> categoriesRaw;
	
	public static Map<Integer, Category> sameCategoryInitiatorMap = new HashMap<Integer, Category>();
	
	public static Map<Integer, dedalus.domain.Category> categoryInitiatorCategoryDedalusMap = new HashMap<Integer, dedalus.domain.Category>();
	
	public static void loadCategories(CategoryService categoryService) throws IOException {
		logger.info("loadCategories");
		
		File categoriesFile = ResourceUtils.getFile("classpath:For Import/Categories.csv");
		categoriesRaw = Files.readAllLines(categoriesFile.toPath());
		
		categoriesRaw.forEach (
			c -> categoriesInitiator.add(buildCategory(c))
		);
		
		categoriesInitiator.forEach (
			cat -> cat.setSameCategory(getSameCategoryToCategory(cat))
		);
		
		persistCategories(categoryService);
	}
	
	private static Category buildCategory(String rawCategory) {
		int id = Integer.parseInt(rawCategory.split(";")[0]);
		String description = rawCategory.split(";")[1];
		
		return new Category(id, description);
	}

	private static Category getSameCategoryToCategory(Category category) {
		for (String categoryRaw: categoriesRaw) {
			String sameCategoryId = categoryRaw.split(";").length==3?categoryRaw.split(";")[2]:null;
			String categoryId = categoryRaw.split(";")[0];
			
			if (sameCategoryId != null) {
				if (category.getId() == Integer.parseInt(categoryId)) {
					return categoriesInitiator.stream().filter (
							c -> c.getId() == Integer.parseInt(sameCategoryId)
					).findFirst().get();
					
				}
			}			
		}
		
		return null;
	}
	
	private static void persistCategories(CategoryService categoryService) {
//		AtomicLong id = new AtomicLong(1);
		
		categoriesInitiator.forEach (
			cat -> {
				
				/*
				System.out.println(String.format("%s. %s: %s", 
						cat.getId(),
						cat.getDescription(), 
						cat.getSameCategory()==null?" Save": "No Save. Same as " + cat.getSameCategory().getDescription() + " (" + cat.getSameCategory().getId() + ")" 
						));
				*/
				
				if (cat.getSameCategory()==null) {
					dedalus.domain.Category category = new dedalus.domain.Category();
					//category.setId(id.getAndIncrement());
					category.setDescription(cat.getDescription());
					
					category = categoryService.save(category);
					
					categoryInitiatorCategoryDedalusMap.put(cat.getId(), category);
				} else {
					sameCategoryInitiatorMap.put(cat.getId(), cat.getSameCategory());
				}
			}
		);		
	}
}
