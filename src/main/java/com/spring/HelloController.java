package com.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.model.Employee;

@RestController
public class HelloController {
	
	
	private static final Logger log = LoggerFactory.getLogger(HelloController.class);


	@Autowired
	Environment env;
	
	@GetMapping(value="/hello")
	public String getName() {
	
		return "Hello "+ env.getProperty("name");
		
	}
	
	@PostMapping(value ="/saveemp")
	public HttpStatus saveEmp(@RequestBody Employee emp) {
		System.out.println("emp id:" +emp.getEmpId());
		System.out.println("emp name:" +emp.getEmpName());
		System.out.println("emp department:" +emp.getDepartment());

		return HttpStatus.ACCEPTED;
	}
}
