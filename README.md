# Employee Directory Project 📝


Overview
This project is an Employee Directory application that allows users to manage employee information and store it in a database. It supports creating, retrieving, updating, and deleting employee details.

## Features:

-Create New Employee: Add a new employee by firstName, lastName and email to the database. POST: /employees

-View All Employees: Retrieve and display a list of all employees. GET: /employees

-View Employee by ID: Look up a specific employee by their unique ID. GET: /employee/{id}

-Update Employee Information: Modify details of an existing employee. PATCH: /employee/{id}

-Delete Employee: Remove an employee record from the database by their ID. DELETE: /employee/{id}

## Technology Stack:

-Backend Framework: Spring, SpringBoot

-Database: PostgreSQL
