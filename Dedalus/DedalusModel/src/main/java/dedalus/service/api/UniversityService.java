package dedalus.service.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dedalus.domain.Faculty;
import dedalus.domain.Location;
import dedalus.domain.University;
import dedalus.domain.structures.EligibleFaculty;
import dedalus.repository.FacultyRepository;
import dedalus.repository.ParameterRepository;
import dedalus.repository.UniversityRepository;

@Service
public class UniversityService extends ParameterService<University> {
	private UniversityRepository universityRepository;
	private FacultyRepository facultyRepository; 
	
	@Autowired
	public UniversityService(UniversityRepository universityRepository, FacultyRepository facultyRepository) {
		this.universityRepository = universityRepository;
		this.facultyRepository = facultyRepository;
	}

	@Override
	public ParameterRepository<University> getParameterRepository() {
		return this.universityRepository;
	}
	
	public List<Faculty> universityFaculties(University university) {
		return this.universityRepository.getUniversityFaculties(university.getId());
	}  
	
	public University updateUniversityFaculties(University university, List<Faculty> faculties) {
		university = this.universityRepository.findOne(university.getId());
		university.setFaculties(new HashSet<>(faculties));
		this.universityRepository.merge(university);
		
		return this.universityRepository.findOne(university.getId());
	}
	
	public List<EligibleFaculty> universityEligibleFaculties(University university) {
		List<Faculty> allFaculties = this.facultyRepository.findAll();  
		
		List<EligibleFaculty> eligibleFaculties = new ArrayList<EligibleFaculty>(allFaculties.size());
		
		allFaculties.forEach (
			f -> eligibleFaculties.add(new EligibleFaculty(f, false))
		);
		
		return eligibleFaculties;
	}

}
