package com.Matthew.Employees.Services;

import com.Matthew.Employees.DAO.EmployeeDAO;
import com.Matthew.Employees.Exceptions.EmployeeNotFoundException;
import com.Matthew.Employees.Models.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private EmployeeDAO employeeDAO;

    public EmployeeService(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        return employeeDAO.findAll();
    }

    // get Employee by Id
    public Employee getEmployeeById(int id) {
        return employeeDAO.findById(id).orElseThrow(()-> new EmployeeNotFoundException("Employee not found with id: " + id));
    }

    // create Employee
    public Employee createEmployee(Employee employee) {
        return employeeDAO.save(employee);
    }
}
