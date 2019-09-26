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
import dedalus.domain.structures.EligibleLocation;
import dedalus.domain.structures.EligibleUniversity;
import dedalus.service.TemporaryEntriesService;
import dedalus.service.api.FacultyService;
import dedalus.service.api.UniversityService;

@Controller
public class FacultyController {
	private FacultyService facultyService;
	private TemporaryEntriesService<Faculty, Location, EligibleLocation> temporaryEntriesService;
	private TemporaryEntriesService<Faculty, University, EligibleUniversity> temporaryEntriesService2;
	
	@Autowired
	public FacultyController(FacultyService facultyService, 
			TemporaryEntriesService<Faculty, Location, EligibleLocation> temporaryEntriesService, 
			TemporaryEntriesService<Faculty, University, EligibleUniversity> temporaryEntriesService2) {
		this.facultyService = facultyService;
		this.temporaryEntriesService = temporaryEntriesService;
		this.temporaryEntriesService2 = temporaryEntriesService2;
		
	}
	
	@RequestMapping("/app/parameters/faculties")
	public String faculties() {
		//this.temporaryEntriesService.init();
		return "/app/parameters/faculties";
	}
	
	@RequestMapping("/data/faculties")
	public @ResponseBody List<Faculty> getFaculties() {
		return this.facultyService.findAll();
	}

	@RequestMapping(value="/data/faculty/create", method = RequestMethod.POST)
	public @ResponseBody Faculty createFaculty() {
		return this.facultyService.create();
	}
	
	@RequestMapping(value="/data/faculty/save", method = RequestMethod.POST)
	public @ResponseBody Faculty saveFacylty(@RequestBody Faculty faculty) {
		return this.facultyService.save(faculty);
	}
	
	@RequestMapping(value="/data/faculty/delete", method = RequestMethod.POST)
	public @ResponseBody Faculty deleteFacylty(@RequestBody Faculty faculty) {
		this.facultyService.delete(faculty);
		
		return faculty;
	}
	
	@RequestMapping(value="/data/faculty/get", method = RequestMethod.POST)
	public @ResponseBody Faculty getFacylty(@RequestBody Faculty faculty) {
		return this.facultyService.get(faculty);
	}
	
	
	/* ------------------------*/
	/* -- Faculty Locations -- */
	/* ------------------------*/
	
	@RequestMapping("/data/faculty/locations")
	public @ResponseBody List<Location> facultyLocations(@RequestBody Faculty faculty) {
		this.temporaryEntriesService.init(faculty);
		this.temporaryEntriesService.setEntriesList(this.facultyService.facultyLocations(faculty));
		
		return this.temporaryEntriesService.getEntriesList();
//		return this.facultyService.facultyLocations(faculty);
	}

	@RequestMapping("/data/faculty/locations/eligible")
	public @ResponseBody List<EligibleLocation> facultyEligibleLocations(@RequestBody Faculty faculty) {
		this.temporaryEntriesService.setEligibleEntriesList(this.facultyService.facultyEligibleLocations(faculty));
		return this.temporaryEntriesService.getEligibleEntriesList();
	}
	
	@RequestMapping("/data/faculty/locations/update")
	public @ResponseBody Faculty updateFacultyLocations(@RequestBody List<Location> locations) {
		Faculty faculty = this.temporaryEntriesService.getEntity();
		
		this.temporaryEntriesService.init(null);
		return this.facultyService.updateFacultyLocations(faculty, locations);
	}
	
	@RequestMapping("/data/faculty/locations/temp/delete")
	public @ResponseBody List<Location> deleteLocationTemporary(@RequestBody Location location) {
		this.temporaryEntriesService.deleteEntry(location);
		
		return temporaryEntriesService.getEntriesList();
	}
	
	@RequestMapping("/data/faculty/locations/temp/add")
	public @ResponseBody List<Location> addLocationsTemporary(@RequestBody List<EligibleLocation> eligibleLocations){
		this.temporaryEntriesService.addSelectedEligibleEntriesToEntries(eligibleLocations);
		
		return this.temporaryEntriesService.getEntriesList();
	}
	

	/* ---------------------------*/
	/* -- Faculty Universities -- */
	/* ---------------------------*/
	
	@RequestMapping("/data/faculty/universities")
	public @ResponseBody List<University> facultyUniversities(@RequestBody Faculty faculty) {
		this.temporaryEntriesService2.init(faculty);
		this.temporaryEntriesService2.setEntriesList(this.facultyService.facultyUniversities(faculty));
		
		return this.temporaryEntriesService2.getEntriesList();
	}
	
	//
	@RequestMapping("/data/faculty/universities/eligible")
	public @ResponseBody List<EligibleUniversity> facultyEligibleUniversities(@RequestBody Faculty faculty) {
		this.temporaryEntriesService2.setEligibleEntriesList(this.facultyService.facultyEligibleUniversities(faculty));
		return this.temporaryEntriesService2.getEligibleEntriesList();
	}
	
	@RequestMapping("/data/faculty/universities/temp/add")
	public @ResponseBody List<University> addUniversitiesTemporary(@RequestBody List<EligibleUniversity> eligibleUniversities){
		this.temporaryEntriesService2.addSelectedEligibleEntriesToEntries(eligibleUniversities);
		
		return this.temporaryEntriesService2.getEntriesList();
	}
	
	@RequestMapping("/data/faculty/universities/temp/delete")
	public @ResponseBody List<University> deleteUniversityTemporary(@RequestBody University university) {
		this.temporaryEntriesService2.deleteEntry(university);
		
		return temporaryEntriesService2.getEntriesList();
	}
	

	@RequestMapping("/data/faculty/universities/update")
	public @ResponseBody Faculty updateFacultyUniversities(@RequestBody List<University> universities) {
		Faculty faculty = this.temporaryEntriesService2.getEntity();
		//this.facultyService.up
		this.temporaryEntriesService2.init(null);
		return this.facultyService.updateFacultyUniversities(faculty, universities);
	}
	
}
