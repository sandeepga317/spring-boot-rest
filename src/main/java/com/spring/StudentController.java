package com.spring;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyNameException;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.entity.StudentEntity;
import com.spring.exception.StudentNotFoundException;
import com.spring.repository.StudentCriteriaExecutor;
import com.spring.repository.StudentRepository;

@RestController
public class StudentController {
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	StudentCriteriaExecutor studentCriteriaExecutor;
	
	@GetMapping(value="/students")
	public ResponseEntity<Iterable<StudentEntity>> getAllStudents(){
		
		Iterable<StudentEntity> students = studentRepository.findAll();
		
		return new ResponseEntity<>(students,HttpStatus.OK);
	}
	
	@GetMapping(value="/students/student")
	public List<StudentEntity> getStudentByName(@RequestParam(required = false, value="name") String name,
			@RequestParam(required = false, value="age") Long age){
		List<StudentEntity> students = null;
		if(name !=null) 
		students = studentRepository.findByName(name);
		else if(age != null) students = studentRepository.findByAgeGreaterThan(age);
		
		return students;
	}
	
	@GetMapping(value="/students/{studentId}")
	public StudentEntity getAllStudentsById(@PathVariable("studentId") Long id){
		
		Optional<StudentEntity> student = studentRepository.findById(id);
		
		if (student.isPresent()) return student.get();
		
		throw new InvalidConfigurationPropertyValueException("id", id, "the student doesnot exist");
	}
	
	@PostMapping(value="/students")
	public Long saveStudent(@RequestBody StudentEntity student) {
		
		studentRepository.save(student);
		return student.getId();
		
	}
	
	@DeleteMapping(value="/students/{studentId}")
	public HttpStatus deleteStudent(@PathVariable("studentId") Long id) {
		
		studentRepository.deleteById(id);
		
		return HttpStatus.ACCEPTED;
	}
	
	@PutMapping(value="/students/{studentId}")
	public HttpStatus updateStudent(@RequestBody StudentEntity student, @PathVariable("studentId") Long id) {
		
		Optional<StudentEntity> studentEntity = studentRepository.findById(id);
		
		if(studentEntity.isPresent()) {
			studentEntity.get().setAge(student.getAge());
			studentEntity.get().setName(student.getName());
			studentRepository.save(studentEntity.get());
			
		}else {
			throw new StudentNotFoundException("Student not found for id:"+id);
		}
		
		return HttpStatus.ACCEPTED;
		
	}
	
	@GetMapping(value="/criteria/students")
	public ResponseEntity<Iterable<StudentEntity>> getAllStudentsFromCriteria(
			@RequestParam(value = "name", required= false) String name,
			@RequestParam(value = "age", required= false) Long age,
			@RequestParam(value = "contact", required= false) String contact){
		Iterable<StudentEntity> students = null;
		students = studentCriteriaExecutor.getStudentsAgeGreater(age,contact, name);
		
		return new ResponseEntity<>(students,HttpStatus.OK);
	}

}
