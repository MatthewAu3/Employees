package com.Matthew.Employees.Controller;

import com.Matthew.Employees.DAO.EmployeeDAO;
import com.Matthew.Employees.Exceptions.EmployeeNotFoundException;
import com.Matthew.Employees.Models.Employee;
import com.Matthew.Employees.Services.EmployeeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeDAO employeeDAO;

    public EmployeeController(EmployeeService employeeService, EmployeeDAO employeeDAO) {
        this.employeeService = employeeService;
        this.employeeDAO = employeeDAO;
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
        } catch (EmployeeNotFoundException e) {
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
        try {
            boolean deleted = employeeService.deleteEmployeeById(id);
            if (deleted) {
                //noContent() creates a builder with the status code 204 No Content, which indicates that the server has successfully processed the request, but there is no content to send back in the response payload.
                //build(): This method is called on the builder created by noContent(). It constructs the ResponseEntity object based on the settings specified in the builder and returns it.
                return ResponseEntity.noContent().build();
            } else {
                throw new EmployeeNotFoundException("Employee not found with id: " + id);
            }
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            // Handle any other exceptions, such as database errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
