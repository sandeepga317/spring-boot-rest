package com.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.spring.entity.StudentEntity;

public interface StudentRepository extends CrudRepository<StudentEntity, Long>{
	
	List<StudentEntity> findByName(String name);
	
	List<StudentEntity> findByAge(Long age);
	
	List<StudentEntity> findByAgeGreaterThan(Long age);

}
