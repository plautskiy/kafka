package ru.plautskiy.projects.kafka.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GenericProducerApp {

	final static String TOPIC="common-input";

	public static void main(String[] args) {
		SpringApplication.run(GenericProducerApp.class, args);
	}

}
