package dedalus.service.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dedalus.domain.Faculty;
import dedalus.domain.Location;
import dedalus.domain.University;
import dedalus.domain.structures.EligibleLocation;
import dedalus.domain.structures.EligibleUniversity;
import dedalus.repository.FacultyRepository;
import dedalus.repository.LocationRepository;
import dedalus.repository.ParameterRepository;
import dedalus.repository.UniversityRepository;

@Service
public class FacultyService  extends ParameterService<Faculty> {
	private FacultyRepository facultyRepository;
	private LocationRepository locationRepository;
	private UniversityRepository  universityRepository;
	
	@Autowired
	public FacultyService(FacultyRepository facultyRepository, LocationRepository locationRepository, UniversityRepository  universityRepository) {
		this.facultyRepository = facultyRepository;
		this.locationRepository = locationRepository;
		this.universityRepository = universityRepository;
	}

	@Override
	public ParameterRepository<Faculty> getParameterRepository() {
		return this.facultyRepository;
	}
	
	@Override
	public Faculty save(Faculty faculty) {
		faculty.setLocations(new HashSet<Location>(this.facultyLocations(faculty)));
		
		return super.save(faculty);
	}
	
	public List<Location> facultyLocations(Faculty faculty) {
		return this.facultyRepository.getFacultyLocations(faculty.getId());
	}
	
	public List<University> facultyUniversities(Faculty faculty) {
		return this.facultyRepository.getFacultyUniversities(faculty.getId());
	} 
	
	public Faculty updateFacultyLocations(Faculty faculty, List<Location> locations) {
		faculty = this.facultyRepository.findOne(faculty.getId());
		
		faculty.setLocations(new HashSet<>(locations));
		
		this.facultyRepository.merge(faculty);
		
		return this.facultyRepository.findOne(faculty.getId());
	}
	
	public List<EligibleLocation> facultyEligibleLocations(Faculty faculty) {
		List<Location> allLocations = this.locationRepository.findAll();
		
		List<EligibleLocation> eligibleLocations = new ArrayList<>(allLocations.size());
		
		allLocations.forEach (
			l -> eligibleLocations.add(new EligibleLocation(l, false))
		);
		
		return eligibleLocations;		
	}
	
	public List<EligibleUniversity> facultyEligibleUniversities(Faculty faculty) {
		List<University> allUniversities = this.universityRepository.findAll();
		
		List<EligibleUniversity> eligibleUniversities = new ArrayList<>(allUniversities.size());
		
		allUniversities.forEach (
			u -> eligibleUniversities.add(new EligibleUniversity(u, false))
		);
		
		return eligibleUniversities;		
	}

	public Faculty updateFacultyUniversities(Faculty faculty, List<University> universities) {
		faculty = this.facultyRepository.findOne(faculty.getId());
		
		faculty.setUniversities(new HashSet<>(universities));
		
		this.facultyRepository.merge(faculty);
		
		return this.facultyRepository.findOne(faculty.getId());
	}	
}