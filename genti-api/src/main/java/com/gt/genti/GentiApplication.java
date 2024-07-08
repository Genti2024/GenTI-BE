package com.gt.genti;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import jakarta.annotation.PostConstruct;

@SpringBootApplication(
	scanBasePackageClasses = {CommonRoot.class, AuthRoot.class, DomainRoot.class, ExternalRoot.class}
)
@ComponentScan("com.gt.genti.config")
public class GentiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GentiApplication.class, args);
	}

	@PostConstruct
	public void setTime() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}
}
