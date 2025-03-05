package es.ua.biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class BibliotecaApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(BibliotecaApplication.class);
		application.setDefaultProperties(Collections.singletonMap("spring.config.name", "application"));
		application.run(args);
	}
}
