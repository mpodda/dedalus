package org.DedalusDataInitiator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication (scanBasePackages = {"dedalus"})
//@ComponentScan(basePackages = {"dedalus"})

@SpringBootApplication
public class DataInitiatorApplication  {
	

	public static void main(String[] args) throws Exception {
		//System.out.println("Start Application");
		SpringApplication.run(DataInitiatorApplication.class, args);
	}
}
