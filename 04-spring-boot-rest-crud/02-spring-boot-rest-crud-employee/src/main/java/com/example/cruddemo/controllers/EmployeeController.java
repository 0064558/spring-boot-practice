package com.example.cruddemo.controllers;

import com.example.cruddemo.entity.Employee;
import com.example.cruddemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> findById(@PathVariable int id) {

        Employee employee = employeeService.findById(id);

        if (employee == null) {
            throw new RuntimeException("Employee id not found - " + id);
        }

        return ResponseEntity.ok(employee);
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmploye(@RequestBody Employee employee) {
        // also just in case they pass an id in JSON... set id to
        // this is to force a save of new item... instead of updates
        employee.setId(0);

        Employee savedEmployee = employeeService.save(employee);
        return ResponseEntity.ok(savedEmployee);
    }
}
