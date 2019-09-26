package dedalus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dedalus.domain.Category;
import dedalus.domain.ScientificField;
import dedalus.service.api.ScientificFieldService;

@Controller
public class ScientificFieldController {
	private ScientificFieldService scientificFieldService;
	
	@Autowired
	public ScientificFieldController(ScientificFieldService scientificFieldService) {
		this.scientificFieldService = scientificFieldService;
	}
	
	@RequestMapping("app/parameters/scientificfields")
	public String scientificFields() {
		return "/app/parameters/scientificFields";
	}
	
	@RequestMapping("data/scientificFields")
	public @ResponseBody List<ScientificField> getScientificField() {
		return this.scientificFieldService.findAll();
	}
	
	
	@RequestMapping(value="data/scientificField/save", method = RequestMethod.POST)
	public @ResponseBody ScientificField saveScientificField(@RequestBody ScientificField scientificField) {
		return this.scientificFieldService.save(scientificField);
	}
	
	@RequestMapping(value="data/scientificField/create", method = RequestMethod.POST)
	public @ResponseBody ScientificField createScientificField() {
		return this.scientificFieldService.create();
	}
	
	@RequestMapping(value="data/scientificField/delete", method = RequestMethod.POST)
	public @ResponseBody ScientificField deleteCategory(@RequestBody ScientificField scientificField) {
		this.scientificFieldService.delete(scientificField);
		
		return scientificField;
	}	
	
}
