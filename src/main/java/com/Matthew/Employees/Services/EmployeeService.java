package com.Matthew.Employees.Services;

import com.Matthew.Employees.DAO.EmployeeDAO;
import com.Matthew.Employees.Exceptions.EmployeeNotFoundException;
import com.Matthew.Employees.Models.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeDAO employeeDAO;

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


    public Employee updateEmployee(Integer id, Employee employee) throws EmployeeNotFoundException{
        Employee existingEmployee = employeeDAO.findById(id).orElseThrow(() ->
                new EmployeeNotFoundException("Employee not found with id: " + id));
        if(employee.getFirstName() != null) existingEmployee.setFirstName(employee.getFirstName());
        if(employee.getLastName() != null) existingEmployee.setLastName(employee.getLastName());
        if(employee.getEmail() != null) existingEmployee.setEmail(employee.getEmail());

        return employeeDAO.save(existingEmployee);
    }


    // Delete Employee by Id
    public boolean deleteEmployeeById(int id) {
        if (employeeDAO.existsById(id)) {
            employeeDAO.deleteById(id);
            return true;
        } else {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }
    }
}
