package org.DedalusDataInitiator2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataInitiatorApplication2 {
	private static final Logger logger = LoggerFactory.getLogger(DataInitiatorApplication2.class);
	
	public static void main(String[] args) throws Exception {
		logger.info("Start Application");
		
		SpringApplication.run(DataInitiatorApplication2.class, args);
		
		logger.info("Stop Application");
	}

}
