package com.itdage.service;


import org.springframework.stereotype.Service;

@Service
//@PropertySource("classpath:test.properties")
public class TestContextService {
	
//	@Value("${name}")
	public String name;
	
	public void test(){
		System.out.println("测试springboot 上下文context");
	}
}
