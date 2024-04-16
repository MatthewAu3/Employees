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

    // Retrieve all employees from the database.
    public List<Employee> getAllEmployees() {
        return employeeDAO.findAll();
    }

    // Retrieve an employee by their unique identifier.
    // Throws an EmployeeNotFoundException if no employee with the specified ID is found.
    public Employee getEmployeeById(int id) {
        return employeeDAO.findById(id).orElseThrow(()-> new EmployeeNotFoundException("Employee not found with id: " + id));
    }

    // Create a new employee in the database.
    public Employee createEmployee(Employee employee) {
        return employeeDAO.save(employee);
    }

    // Update an existing employee's information in the database.
    // Throws an EmployeeNotFoundException if the employee with the specified ID is not found.
    public Employee updateEmployee(Integer id, Employee employee) throws EmployeeNotFoundException{
        Employee existingEmployee = employeeDAO.findById(id).orElseThrow(() ->
                new EmployeeNotFoundException("Employee not found with id: " + id));
        if(employee.getFirstName() != null) existingEmployee.setFirstName(employee.getFirstName());
        if(employee.getLastName() != null) existingEmployee.setLastName(employee.getLastName());
        if(employee.getEmail() != null) existingEmployee.setEmail(employee.getEmail());

        return employeeDAO.save(existingEmployee);
    }

    // Delete an employee from the database by their ID.
    // Throws an EmployeeNotFoundException if no employee with the specified ID is found.
    public boolean deleteEmployeeById(int id) {
        if (employeeDAO.existsById(id)) {
            employeeDAO.deleteById(id);
            return true;
        } else {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }
    }
}
