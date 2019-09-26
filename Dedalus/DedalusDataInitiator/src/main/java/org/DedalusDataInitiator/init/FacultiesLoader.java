package org.DedalusDataInitiator.init;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.DedalusDataInitiator.init.domain.Faculty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import dedalus.service.api.FacultyService;

public final class FacultiesLoader {
	private static final Logger logger = LoggerFactory.getLogger(FacultiesLoader.class);
	
	private static List<Faculty> facultiesInitiator = new ArrayList<Faculty>();
	private static List<String> facultiesRaw;
	
	public static Map<Integer, dedalus.domain.Faculty> facultyInitiatorFacultyDedalusMap = new HashMap<Integer, dedalus.domain.Faculty>();
	
	public static void loadFaculties(FacultyService facultyService) throws IOException {
		logger.info("loadFaculties");
		
		File facultiesFile = ResourceUtils.getFile("classpath:For Import/Faculties.csv");
		facultiesRaw = Files.readAllLines(facultiesFile.toPath());
		
		facultiesRaw.forEach (
			fr -> facultiesInitiator.add(buildFaculty(fr))
		);
	
		persistFaculties(facultyService);
	}
	
	private static Faculty buildFaculty(String rawFaculty) {
		int id = Integer.parseInt(rawFaculty.split(";")[0]);
		String description = rawFaculty.split(";")[1];
		
		Faculty faculty = new Faculty(id, description);
		
		return faculty;
	}	

	
	private static void persistFaculties(FacultyService facultyService) {
//		AtomicLong id = new AtomicLong(1);
		
		facultiesInitiator.forEach (
			facl -> {
				dedalus.domain.Faculty faculty = new dedalus.domain.Faculty();
				
//				faculty.setId(id.getAndIncrement());
				faculty.setDescription(facl.getDescription());
				
				faculty = facultyService.save(faculty);
				
				facultyInitiatorFacultyDedalusMap.put(facl.getId(), faculty);
			} 
		);
	}
}
