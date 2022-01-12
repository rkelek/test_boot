package com.test.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableRetry
@EnableCaching
@SpringBootApplication
public class TestBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestBootApplication.class, args);
	}

	@Bean(destroyMethod = "destroy")
	public ShutdownHookConfiguration shutdownHookConfiguration() {
		System.setProperty("jsse.enableSNIExtension", "false");
		return new ShutdownHookConfiguration();
	}
}
