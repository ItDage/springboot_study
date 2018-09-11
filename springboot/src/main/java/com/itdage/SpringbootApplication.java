package com.itdage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(SpringbootApplication.class);
//		application.addListeners(new MyApplicationStartingEvent(), new MyApplicationEnvironmentPreparedEvent(),
//				new MyApplicationPreparedEvent(), new MyApplicationReadyEventListener());
//		
		application.run(args);
	}
}
