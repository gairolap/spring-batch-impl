/**
 * Main class for Spring Batch Application.
 */
package com.jpmorgan.gwmft.batch.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.jpmorgan.gwmft" })
public class SpringBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchApplication.class, args);
	}

}