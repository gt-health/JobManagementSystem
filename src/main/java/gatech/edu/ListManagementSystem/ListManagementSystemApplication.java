package gatech.edu.ListManagementSystem;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAutoConfiguration
@EntityScan("gatech.edu.ListManagementSystem.model")
@EnableJpaRepositories("gatech.edu.ListManagementSystem.repo")
@ComponentScan("gatech.edu.ListManagementSystem.controller")
@ComponentScan("gatech.edu.ListManagementSystem")
@EnableScheduling
@SpringBootApplication
public class ListManagementSystemApplication extends SpringBootServletInitializer{

	private static final Logger log = LoggerFactory.getLogger(ListManagementSystemApplication.class);

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ListManagementSystemApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(ListManagementSystemApplication.class);
	}
	
}