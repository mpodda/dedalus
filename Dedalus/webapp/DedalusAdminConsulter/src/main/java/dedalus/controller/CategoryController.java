package dedalus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dedalus.domain.Category;
import dedalus.service.api.CategoryService;

@Controller
public class CategoryController implements MessageSourceAware {
	private CategoryService categoryService;
	private MessageSource messageSource;
	
	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@RequestMapping("app/parameters/categories")
	public String categories(Model model) {
		model.addAttribute("locale", LocaleContextHolder.getLocale().getLanguage());
		
		return "/app/parameters/categories";
	}
	
	@RequestMapping("data/categories")
	public @ResponseBody List<Category> getCategories() {
		return this.categoryService.findAll();
	}

	@RequestMapping(value="data/category/create", method = RequestMethod.POST)
	public @ResponseBody Category createCategory() {
		return this.categoryService.create();
	}
	
	@RequestMapping(value="data/category/save", method = RequestMethod.POST)
	public @ResponseBody Category saveCategory(@RequestBody Category category) {
		return this.categoryService.save(category);
	}
	
	@RequestMapping(value="data/category/delete", method = RequestMethod.POST)
	public @ResponseBody Category deleteCategory(@RequestBody Category category) {
		this.categoryService.delete(category);
		
		return category;
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}	
}