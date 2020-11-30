package com.mvfinapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.mvfinapp.config.ApplicationPropertyConfig;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationPropertyConfig.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
