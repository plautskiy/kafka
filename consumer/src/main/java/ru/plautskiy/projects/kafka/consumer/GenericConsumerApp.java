package ru.plautskiy.projects.kafka.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GenericConsumerApp {

	final static String TOPIC="common-input";

	public static void main(String[] args) {
		SpringApplication.run(GenericConsumerApp.class, args);
	}

}
