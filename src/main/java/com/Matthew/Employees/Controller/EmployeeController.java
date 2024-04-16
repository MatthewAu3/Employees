package com.Matthew.Employees.Controller;

import com.Matthew.Employees.DAO.EmployeeDAO;
import com.Matthew.Employees.Exceptions.EmployeeNotFoundException;
import com.Matthew.Employees.Models.Employee;
import com.Matthew.Employees.Services.EmployeeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController  // Marks the class as a REST controller
@RequestMapping("/employees")  // Sets the base URL for all requests in this controller
public class EmployeeController {

    private final EmployeeService employeeService;  // Dependency injection for the employee service

    // Constructor that injects the EmployeeService
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Endpoint to get all employees
    @GetMapping
    public List<Employee> getAllEmployeesHandler() {
        return employeeService.getAllEmployees();  // Calls the service method to get all employees
    }

    // Endpoint to get a specific employee by ID
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        try {
            Employee employee = employeeService.getEmployeeById(id);  // Attempts to find an employee by ID
            return new ResponseEntity<>(employee, OK);  // Returns employee details with status 200 OK
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);  // Returns 404 NOT FOUND if the employee does not exist
        }
    }

    // Endpoint to create a new employee
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);  // Calls the service method to create a new employee
    }

    // Endpoint to update an employee by ID
    @PatchMapping("{id}")
    public ResponseEntity<String> updateEmployeeById(@PathVariable Integer id, @RequestBody Employee employee) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, employee);  // Attempts to update an employee
            return ResponseEntity.ok("Employee updated successfully with ID: " + updatedEmployee.getEmployeeId());  // Successful update returns 200 OK with a message
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());  // On failure, returns 400 BAD REQUEST with error message
        }
    }

    // Endpoint to delete an employee by ID
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Integer id) {
        try {
            boolean deleted = employeeService.deleteEmployeeById(id);  // Attempts to delete an employee by ID
            if (deleted) {
                return ResponseEntity.ok("Employee " + id + " deleted successfully.");  // If deleted, returns 200 OK with a message
            } else {
                throw new EmployeeNotFoundException("Employee not found with id: " + id);  // If not found, throws an exception
            }
        } catch (EmployeeNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);  // Catches the exception and throws ResponseStatusException with 404 NOT FOUND
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", e);  // Handles any other exception and throws ResponseStatusException with 500 INTERNAL SERVER ERROR
        }
    }
}
