package com.itdage.springboot;

import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;


public class MyApplicationPreparedEvent implements ApplicationListener<ApplicationPreparedEvent>{

	@Override
	public void onApplicationEvent(ApplicationPreparedEvent event) {
		//获取到上下文context后,可以将其注入到其他类中
		ConfigurableApplicationContext context = event.getApplicationContext();
//      下面代码报错,此时还没有bean
//		TestContextService testContextService = (TestContextService)context.getBean("testContextService");
//		testContextService.test();
		
	}
}
