package com.yilnz.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * curl http://localhost:8080
 */
@SpringBootApplication(scanBasePackages = "com.yilnz.boot")
@MapperScan(basePackages = "com.yilnz.boot.dao")
@ImportResource(locations = "spring/spring.xml")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
