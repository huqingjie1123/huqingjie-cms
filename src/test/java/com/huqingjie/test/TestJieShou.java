package com.huqingjie.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestJieShou {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("classpath:consumer.xml");
	}
	
}
