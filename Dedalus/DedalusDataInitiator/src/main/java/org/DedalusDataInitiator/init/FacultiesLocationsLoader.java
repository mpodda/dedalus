package org.DedalusDataInitiator.init;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.DedalusDataInitiator.init.domain.FacultyLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import dedalus.service.api.FacultyService;
import dedalus.service.api.LocationService;

public class FacultiesLocationsLoader {
	private static final Logger logger = LoggerFactory.getLogger(FacultiesLocationsLoader.class);
	
	private static Set<FacultyLocation> facultyLocationsInitiatorSet = new java.util.HashSet<FacultyLocation>();
	
	private static List<String> facultyLocationsRaw;
	
	public static void loadFacultyLocations(LocationService locationService,
			FacultyService facultyService,
			Map<Integer, dedalus.domain.Faculty> facultyInitiatorFacultyDedalusMap,
			Map<Integer, dedalus.domain.Location> locationInitiatorLocationDedalusMap) throws IOException {
		
		logger.info("loadFacultyLocations");
		
		File facultyLocationsFile = ResourceUtils.getFile("classpath:For Import/FacultiesLocations.csv");
		facultyLocationsRaw = Files.readAllLines(facultyLocationsFile.toPath());
		
		facultyLocationsRaw.forEach (
			flr -> facultyLocationsInitiatorSet.add(buildFacultyLocation(flr))
		);
		
		persistFacultyLocations(locationService, facultyService, facultyInitiatorFacultyDedalusMap, locationInitiatorLocationDedalusMap);
	}
	
	private static FacultyLocation buildFacultyLocation(String rawFacultyLocation) {
		int facultyId = Integer.parseInt(rawFacultyLocation.split(";")[0]);
		String facultyDescription = rawFacultyLocation.split(";")[1];
		
		int locationId = Integer.parseInt(rawFacultyLocation.split(";")[2]);
		String locationDescription = rawFacultyLocation.split(";")[3];
		
		FacultyLocation facultyLocation = new FacultyLocation(facultyId, facultyDescription, locationId, locationDescription);
		
		return facultyLocation;
	}
	
	private static void persistFacultyLocations(LocationService locationService,
			FacultyService facultyService,
			Map<Integer, dedalus.domain.Faculty> facultyInitiatorFacultyDedalusMap,
			Map<Integer, dedalus.domain.Location> locationInitiatorLocationDedalusMap) {
		
		facultyInitiatorFacultyDedalusMap.forEach (
			(rawFacultyId, dedalusFaculty) -> {
				
				List<dedalus.domain.Location> locations = new ArrayList<dedalus.domain.Location>();
				
				facultyLocationsInitiatorSet.forEach (
					fli -> {
						if (fli.getFaculty().getId() == rawFacultyId.intValue()) {
							dedalus.domain.Location location = locationInitiatorLocationDedalusMap.get(fli.getLocation().getId());
							locations.add(location);
						}
					}
				);
				
				facultyService.updateFacultyLocations(dedalusFaculty, locations);
			}
		);
	}
}