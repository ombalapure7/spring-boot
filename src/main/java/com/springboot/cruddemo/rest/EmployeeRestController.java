package com.springboot.cruddemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.cruddemo.entity.Employee;
import com.springboot.cruddemo.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
	private EmployeeService employeeService;
	// inject employee DAO
	@Autowired
	public EmployeeRestController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	// expose "/employees and return list of employees"
	@GetMapping("/employees")
	public List<Employee> findAll() {
		return employeeService.findAll();
	}
	
	// add GET mapping -- /employees/{employeeId}
	@GetMapping("/employees/{employeeId}")
	public Employee getEmployee(@PathVariable int employeeId) {
		Employee theEmployee = employeeService.findById(employeeId);
		
		if (theEmployee == null) {
			throw new RuntimeException("Employee id not found: " +employeeId);
		}
		
		return theEmployee;
	}
	
	// add POST mapping -- /employees - add new employee
	@PostMapping("/employees") 
	// the employee data is going to come in Request Body as JSON
	public Employee addEmployee(@RequestBody Employee theEmployee) {
		// also in case they pass an id in JSON.. set id to 0
		// this is to force to save new item...instead of update
		theEmployee.setId(0);
		
		employeeService.save(theEmployee);
	
		return theEmployee;
	}
	
	// add PUT mapping -- /employees - update existing employee
	@PutMapping("/employees")
	public Employee updateEmployee(@RequestBody Employee theEmployee) {
		employeeService.save(theEmployee);
		return theEmployee;
	}
	
	// add DELETE mapping -- /employees - delete existing employee
	@DeleteMapping("/employees/{employeeId}")
	public String deleteEmployee(@PathVariable int employeeId) {
		Employee theEmployee = employeeService.findById(employeeId);
		
		// throw exception if null
		if (theEmployee == null) {
			throw new RuntimeException("Employee never existed in database - " +employeeId);
		}
		
		employeeService.deleteById(employeeId);
		
		return "Deleted an employee id of: " +employeeId;
	}
	
}
