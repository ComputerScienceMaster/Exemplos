package br.com.csm.LearnToImplementspringrest;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import br.com.csm.LearnToImplementspringrest.student.StudentResource;

@SpringBootApplication
public class LearnToImplementSpringRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnToImplementSpringRestApplication.class, args);
	}
	
}

