package com.myapp.spring.service;

import java.util.List;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.myapp.spring.model.Employee;
import com.myapp.spring.model.EmployeeMapper;




@Service //since it is a service
public class EmployeeServiceImpl implements EmployeeService{
	
	JdbcTemplate jdbcTemplate;
	

	//our table name is employee it is in 'firstdb'  database
	private final String SQL_FIND = "select * from employee where id = ?";
	private final String SQL_DELETE = "delete from employee where id = ?";
	private final String SQL_UPDATE = "update employee set name = ?, age = ?, gender  = ?,salary = ?  where id = ?";
	private final String SQL_GET_ALL = "select * from employee";
	private final String SQL_INSERT = "insert into employee(id, name, age, gender,salary) values(?,?,?,?,?)";
	
	
	

	
	@Autowired
	public EmployeeServiceImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		
	}
	
	
	
	@Override
	@Transactional//letus replace with javax if this didn't work out 
	public int save(Employee employee) {
		// TODO Auto-generated method stub
		//return 0;
		
		
		jdbcTemplate.update(SQL_INSERT,employee.getId(),employee.getName(),employee.getAge(),employee.getGender(),employee.getSalary() );
		
		return employee.getId();
		
		
	}

	@Override
	@Transactional
	public Employee get(int id) {
		// TODO Auto-generated method stub
		return jdbcTemplate.queryForObject(SQL_FIND, new Object[] { id }, new EmployeeMapper());
	}

	@Override
	@Transactional
	public List<Employee> list() {
		// TODO Auto-generated method stub
		return jdbcTemplate.query(SQL_GET_ALL, new EmployeeMapper());
	}

	@Transactional
	@Override
	public void update(int id, Employee employee) {
		// TODO Auto-generated method stub
		jdbcTemplate.update(SQL_UPDATE,employee.getName(), employee.getAge(),employee.getGender(),employee.getSalary(),
				 employee.getId());
		
	}

	@Transactional
	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		 jdbcTemplate.update(SQL_DELETE, id);
	}

	
	
}
