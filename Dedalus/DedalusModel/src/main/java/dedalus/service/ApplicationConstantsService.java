package dedalus.service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;

import dedalus.ApplicationStringConstants;

@Service
public class ApplicationConstantsService {
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ApplicationStringConstants.DATE_FORMAT.getValue());
	private static NumberFormat numberFormatter = new DecimalFormat("#0.00");
			
	public static SimpleDateFormat dateFormat() {
		 return simpleDateFormat;
	}
	
	public static NumberFormat numberFormatter() {
		return numberFormatter;
	}
}
