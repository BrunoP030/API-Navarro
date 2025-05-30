package com.healthmonitoring.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MonitoringApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitoringApiApplication.class, args);
		System.out.println("🏥 Monitoring API está rodando!");
		System.out.println("📊 Swagger UI: http://localhost:8083/swagger-ui.html");
		System.out.println("📋 API Docs: http://localhost:8083/v3/api-docs");
	}
}
