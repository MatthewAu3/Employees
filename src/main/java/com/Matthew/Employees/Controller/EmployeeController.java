package com.Matthew.Employees.Controller;

import com.Matthew.Employees.DAO.EmployeeDAO;
import com.Matthew.Employees.Exceptions.EmployeeNotFoundException;
import com.Matthew.Employees.Models.Employee;
import com.Matthew.Employees.Services.EmployeeService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    EmployeeService employeeService;
    EmployeeDAO employeeDAO;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployeesHandler() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        Employee employee;

        try {
            employee = employeeService.getEmployeeById(id);
        } catch(EmployeeNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(employee, OK);
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Integer id) {
        if (employeeDAO.existsById(id)) {
            employeeDAO.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
