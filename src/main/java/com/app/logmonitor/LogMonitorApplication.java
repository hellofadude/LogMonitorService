package com.app.logmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.app.logmonitor.storage.StorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class LogMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogMonitorApplication.class, args);
	}

}
