package dedalus.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class RandomNumberGenerator {
	static int[] mulFactor = {10, 100, 1000, 10000, 100000};
	
	public static int generateRandomNumber (int minNumber, int maxNumber) {
		
		while (true){
			for (int j=0; j<mulFactor.length; j++){
				double d = Math.random();
				d = d * mulFactor[randomMulFactorIndex()];
				int newNumber = new Double(d).intValue();
				if (newNumber >= minNumber && newNumber <= maxNumber) {
					return newNumber;
				}
			}
		}		
	}
	
	private static int randomMulFactorIndex() {
		int minIndex = 3;
		int maxIndex = 4;
		
		while (true){
			for (int j=0; j<mulFactor.length; j++){
				double d = Math.random();
				d = d * mulFactor[randomMulFactorIndexStep2()];
	
				int newNumber = new Double(d).intValue();
				
				if (newNumber >= minIndex && newNumber <= maxIndex) {
					return newNumber;
				}
			}
		}
	}
	
	
	private static int randomMulFactorIndexStep2() {
		int minIndex = 0;
		int maxIndex = 2;
		
		while (true){
			for (int j=0; j<mulFactor.length; j++){
				double d = Math.random();
				d = d * mulFactor[j];
	
				int newNumber = new Double(d).intValue();
				
				if (newNumber >= minIndex && newNumber <= maxIndex) {
					return newNumber;
				}
			}
		}
	}	

	
	public static void main(String[] args) {
		
		//System.out.println(new Double(Math.random() * 10).intValue());
		
		
		List<Integer> positionsList = new ArrayList<Integer>();
		
		for (int i=0; i<344; i++) {
			int pos = generateRandomNumber(1, 344);
		
			
			while (positionsList.contains(new Integer(pos))) {
				pos = generateRandomNumber(1, 344);
			}
			
			positionsList.add(new Integer(pos));
			
		}
		
		
		AtomicInteger current = new AtomicInteger(1);
		
		positionsList.forEach (
			pos ->  {
				System.out.println(pos);
				if (current.getAndIncrement() % 5 == 0) {
					System.out.println("--------------------------------");
				}
			}
		);
		
			
	}
}
