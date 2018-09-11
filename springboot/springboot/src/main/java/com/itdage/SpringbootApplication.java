package com.itdage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.itdage.springboot.MyApplicationEnvironmentPreparedEvent;
import com.itdage.springboot.MyApplicationPreparedEvent;
import com.itdage.springboot.MyApplicationReadyEventListener;
import com.itdage.springboot.MyApplicationStartingEvent;

@SpringBootApplication
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(SpringbootApplication.class);
		application.addListeners(new MyApplicationStartingEvent(), new MyApplicationEnvironmentPreparedEvent(),
				new MyApplicationPreparedEvent(), new MyApplicationReadyEventListener());
		
		application.run(args);
	}
}
