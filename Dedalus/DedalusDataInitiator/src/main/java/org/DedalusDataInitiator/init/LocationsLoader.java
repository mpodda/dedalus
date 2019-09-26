package org.DedalusDataInitiator.init;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.DedalusDataInitiator.init.domain.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import dedalus.service.api.LocationService;

public final class LocationsLoader {
	private static final Logger logger = LoggerFactory.getLogger(LocationsLoader.class);
	
	private static List<Location> locationsInitiator = new ArrayList<Location>();
	private static List<String> locationsRaw;
	
	public static Map<Integer, dedalus.domain.Location> locationInitiatorLocationDedalusMap = new HashMap<Integer, dedalus.domain.Location>();
	
	public static void loadLocations(LocationService locationService) throws IOException {
		logger.info("loadLocations");
		
		File locationsFile = ResourceUtils.getFile("classpath:For Import/Locations.csv");
		locationsRaw = Files.readAllLines(locationsFile.toPath());
		
		locationsRaw.forEach (
			lr -> locationsInitiator.add(buildLocation(lr))
		);
		
		persistLocations(locationService);
	}
	
	private static Location buildLocation(String rawLocation) {
		int id = Integer.parseInt(rawLocation.split(";")[0]);
		String description = rawLocation.split(";")[1];
		String sameLocationId = rawLocation.split(";").length==3?rawLocation.split(";")[2]:null;
		
		return new Location (id, description, sameLocationId != null);
	}
	
	private static void persistLocations(LocationService locationService) {
		AtomicLong id = new AtomicLong(1);
		
		locationsInitiator.forEach (
			l -> {
				
				if (!l.isHasSameLocation()) {
					dedalus.domain.Location location = new dedalus.domain.Location();
//					location.setId(id.getAndIncrement());
					location.setDescription(l.getDescription());
					
					location = locationService.save(location);
					
					locationInitiatorLocationDedalusMap.put(l.getId(), location);
				}
			}
		);
	}
}
