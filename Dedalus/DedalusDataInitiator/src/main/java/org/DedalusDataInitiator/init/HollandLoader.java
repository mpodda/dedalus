package org.DedalusDataInitiator.init;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.DedalusDataInitiator.init.domain.Holland;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import dedalus.service.api.HollandService;

public final class HollandLoader {
	private static final Logger logger = LoggerFactory.getLogger(HollandLoader.class);
	
	private static List<Holland> hollandsInitiator = new ArrayList<Holland>();
	private static List<String> hollandsRaw;
	
	public static Map<Integer, dedalus.domain.Holland> hollanndInitiatorHollandDedalusMap = new HashMap<Integer, dedalus.domain.Holland>();
	
	public static void loadHollands(HollandService hollandService) throws IOException {
		logger.info("loadHollands");
		
		File hollandsFile = ResourceUtils.getFile("classpath:For Import/Hollands.csv");
		hollandsRaw = Files.readAllLines(hollandsFile.toPath());
		
		
		hollandsRaw.forEach (
			hr -> {
				hollandsInitiator.add(buildHolland(hr));
			}
		);

		persistHolland(hollandService);
	}
	
	private static Holland buildHolland(String rawHolland) {
		return new Holland(Integer.parseInt(rawHolland));
	}
	
	private static void persistHolland(HollandService hollandService) {
		hollandsInitiator.forEach (
			hi -> {
				dedalus.domain.Holland holland = new dedalus.domain.Holland();
				holland.setValue(hi.getValue());
				
				holland = hollandService.save(holland);
				
				hollanndInitiatorHollandDedalusMap.put(hi.getValue(), holland);
			}
		);
	}
	
}
