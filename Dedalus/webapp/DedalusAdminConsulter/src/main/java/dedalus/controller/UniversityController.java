package dedalus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dedalus.domain.Faculty;
import dedalus.domain.Location;
import dedalus.domain.University;
import dedalus.domain.structures.EligibleFaculty;
import dedalus.domain.structures.EligibleLocation;
import dedalus.service.TemporaryEntriesService;
import dedalus.service.api.UniversityService;

@Controller
public class UniversityController {
	private UniversityService universityService;
	private TemporaryEntriesService<University, Faculty, EligibleFaculty> temporaryEntriesService;
	
	@Autowired
	public UniversityController(UniversityService universityService, TemporaryEntriesService<University, Faculty, EligibleFaculty> temporaryEntriesService) {
		this.universityService = universityService;
		this.temporaryEntriesService = temporaryEntriesService;
	}
	
	@RequestMapping("/app/parameters/universities")
	public String faculties() {
		return "/app/parameters/universities";
	}
	
	@RequestMapping("/data/universities")
	public @ResponseBody List<University> getUniversities() {
		return this.universityService.findAll();
	}
	
	@RequestMapping(value="/data/university/create", method = RequestMethod.POST)
	public @ResponseBody University createUniversity() {
		return this.universityService.create();
	}
	
	@RequestMapping(value="/data/university/save", method = RequestMethod.POST)
	public @ResponseBody University saveUniversityy(@RequestBody University university) {
		return this.universityService.save(university);
	}
	
	@RequestMapping(value="/data/university/delete", method = RequestMethod.POST)
	public @ResponseBody University deleteFacylty(@RequestBody University university) {
		this.universityService.delete(university);
		
		return university;
	}
	
	@RequestMapping(value="/data/university/get", method = RequestMethod.POST)
	public @ResponseBody University getFacylty(@RequestBody University university) {
		return this.universityService.get(university);
	}

	@RequestMapping(value="/data/university/faculties", method = RequestMethod.POST)
	public @ResponseBody List<Faculty> universityFaculties(@RequestBody University university) {
		this.temporaryEntriesService.init(university);
		this.temporaryEntriesService.setEntriesList(this.universityService.universityFaculties(university));
		
		return this.temporaryEntriesService.getEntriesList();
	}
	
	@RequestMapping("/data/university/faculties/eligible")
	public @ResponseBody List<EligibleFaculty> universityEligibleFaculties(@RequestBody University university) {
		this.temporaryEntriesService.setEligibleEntriesList(this.universityService.universityEligibleFaculties(university));
		
//		List<EligibleFaculty> l = this.temporaryEntriesService.getEligibleEntriesList();
//		
//		System.out.println(String.format("university=%s  eligible faculties=%s",  
//				university.getDescription(), l == null?"NULL":l.size()));
		
		return this.temporaryEntriesService.getEligibleEntriesList();
	}
	
	@RequestMapping("/data/university/faculties/update")
	public @ResponseBody University updateUniversityFacultιes(@RequestBody List<Faculty> faculties) {
		University university = this.temporaryEntriesService.getEntity();
		
		this.temporaryEntriesService.init(null);
		
		return this.universityService.updateUniversityFaculties(university, faculties);
	}
	
	@RequestMapping("/data/university/faculties/temp/add")
	public @ResponseBody List<Faculty> adFacultiesTemporary(@RequestBody List<EligibleFaculty> eligibleFaculties){
		this.temporaryEntriesService.addSelectedEligibleEntriesToEntries(eligibleFaculties);
		
		return this.temporaryEntriesService.getEntriesList();
	}
	
	@RequestMapping("/data/university/faculties/temp/delete")
	public @ResponseBody List<Faculty> deleteFacultyTemporary(@RequestBody Faculty faculty) {
		this.temporaryEntriesService.deleteEntry(faculty);
		return temporaryEntriesService.getEntriesList();
	}
	
}
