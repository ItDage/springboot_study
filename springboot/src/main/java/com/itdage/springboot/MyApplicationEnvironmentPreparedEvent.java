package com.itdage.springboot;

import java.util.Iterator;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

public class MyApplicationEnvironmentPreparedEvent implements ApplicationListener<ApplicationEnvironmentPreparedEvent>{

	@Override
	public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
		ConfigurableEnvironment environment = event.getEnvironment();
		MutablePropertySources sources = environment.getPropertySources();
		Iterator<PropertySource<?>> iterator = sources.iterator();
		while(iterator.hasNext()){
			PropertySource<?> propertySource = iterator.next();
			System.out.print("配置名称:" + propertySource.getName());
			System.out.print("配置资源:" + propertySource.getSource());
			System.out.println("配置类:" +propertySource.getClass());
		}
		
	}
}
