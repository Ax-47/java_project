package com.example.restservice;

import org.springframework.context.ApplicationContext;

import java.beans.Customizer;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
public class RestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestServiceApplication.class, args);
	}

	// @Bean
	// BeanFactoryPostProcessor beanFactoryPostProcessor(ApplicationContext
	// beanRegistry) {
	// return beanFactory -> {
	// genericApplicationContext(
	// (BeanDefinitionRegistry)
	// ((AnnotationConfigServletWebServerApplicationContext) beanRegistry)
	// .getBeanFactory());
	// };
	// }

	// void genericApplicationContext(BeanDefinitionRegistry beanRegistry) {
	// ClassPathBeanDefinitionScanner beanDefinitionScanner = new
	// ClassPathBeanDefinitionScanner(beanRegistry);
	// beanDefinitionScanner.addIncludeFilter(removeModelAndEntitiesFilter());
	// beanDefinitionScanner.scan("com.baeldung.pattern.cleanarchitecture");
	// }
	//
	// static TypeFilter removeModelAndEntitiesFilter() {
	// return (MetadataReader mr, MetadataReaderFactory mrf) ->
	// !mr.getClassMetadata()
	// .getClassName()
	// .endsWith("Model");
	// }
}
