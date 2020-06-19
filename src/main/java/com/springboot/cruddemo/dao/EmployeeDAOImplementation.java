package com.springboot.cruddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springboot.cruddemo.entity.Employee;

@Repository
public class EmployeeDAOImplementation implements EmployeeDAO {
	// define entity manager field
	private EntityManager entityManager;

	// set up constructor injection
	@Autowired
	public EmployeeDAOImplementation(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	//@org.springframework.transaction.annotation.Transactional
	// this prevents from manually committing transactions
	// ==>  we can also create a service to this for us 
	public List<Employee> findAll() {
		// get current hibernate session
		// get current hibernate session using this
		Session currentSession = entityManager.unwrap(Session.class);

		// create query
		Query<Employee> theQuery = currentSession.createQuery("from Employee", Employee.class);

		// execute query and get result list
		List<Employee> employees = theQuery.getResultList();

		// return result list
		return employees;
	}

	@Override
	public Employee findById(int theId) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Employee theEmployee = currentSession.get(Employee.class, theId);
		
		return theEmployee;
	}

	@Override
	public void save(Employee theEmployee) {
		Session currentSession = entityManager.unwrap(Session.class);
		// if id = 0 then "save"
		// else "update" 
		currentSession.saveOrUpdate(theEmployee);
	}

	@Override
	public void deleteById(int theId) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = currentSession.createQuery("delete from Employee where id=:employeeId");
		
		theQuery.setParameter("employeeId",theId);
		
		theQuery.executeUpdate(); 
	}
}
