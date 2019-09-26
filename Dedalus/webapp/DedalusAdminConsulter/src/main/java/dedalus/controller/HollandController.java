package dedalus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dedalus.domain.Holland;
import dedalus.service.api.HollandService;

@Controller
public class HollandController {
	private HollandService hollandService;

	@Autowired
	public HollandController(HollandService hollandService) {
		this.hollandService = hollandService;
	}

	@RequestMapping("app/parameters/hollands")
	public String hollands() {
		return "/app/parameters/hollands";
	}
	
	@RequestMapping("data/hollands")
	public @ResponseBody List<Holland> getHollands() {
		return this.hollandService.findAll();
	}
	
	@RequestMapping(value="data/holland/create", method = RequestMethod.POST)
	public @ResponseBody Holland createHolland() {
		return this.hollandService.create();
	}
	
	@RequestMapping(value="data/holland/save", method = RequestMethod.POST)
	public @ResponseBody Holland saveHolland(@RequestBody Holland holland) {
		return this.hollandService.save(holland);
	}

	@RequestMapping(value="data/holland/delete", method = RequestMethod.POST)
	public @ResponseBody Holland deleteHolland(@RequestBody Holland holland) {
		this.hollandService.delete(holland);
		
		return holland;
	}	
}
