package com.spring.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.entity.StudentContactEntity;
import com.spring.entity.StudentEntity;

@Service
public class StudentCriteriaExecutor {
	
	@Autowired
	EntityManager em;
	
	private Session getCurrentSession() {
		return  em.unwrap(Session.class);
	}
	
	public List<StudentEntity> getStudents(){
		Criteria criteria = getCurrentSession().createCriteria(StudentEntity.class);
		
		return criteria.list();
	}
	
	public List<StudentEntity> getStudentsAgeGreater(Long age, String contact, String name){
		Criteria criteria = getCurrentSession().createCriteria(StudentEntity.class);
		if(age!=null) criteria.add(Restrictions.gt("age", age));
		if(name != null) criteria.add(Restrictions.eq("name", name));
		if(contact!=null) {
			criteria.createAlias("studentContacts", "studentContacts");
			criteria.add(Restrictions.eq("studentContacts.contactNumber",contact));
		}
		
		return criteria.list();
	}
	
	
}
