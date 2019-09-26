package org.DedalusDataInitiator.simple.service;

public class TestStatic {
	public static String text = "Init Text";
	
	public static void setText() {
		text = "Hello with static way";
	}
	
	
	public static void main(String[] args) {
		TestStatic.setText();
		
		System.out.println(TestStatic.text);
	}
}
