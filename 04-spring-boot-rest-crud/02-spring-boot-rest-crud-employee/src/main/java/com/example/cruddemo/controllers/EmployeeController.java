package com.example.cruddemo.controllers;

import com.example.cruddemo.entity.Employee;
import com.example.cruddemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    private EmployeeService employeeService;

    // convert Java objects to JSON and JSON to Java objects
    private JsonMapper jsonMapper;

    @Autowired
    public EmployeeController(EmployeeService employeeService, JsonMapper jsonMapper) {
        this.employeeService = employeeService;
        this.jsonMapper = jsonMapper;
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> findById(@PathVariable int id) {

        Employee employee = employeeService.findById(id);

        return ResponseEntity.ok(employee);
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmploye(@RequestBody Employee employee) {
        // also just in case they pass an id in JSON... set id to
        // this is to force a save of new item... instead of updates
        employee.setId(0);

        Employee savedEmployee = employeeService.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    @PutMapping("/employees")
    public ResponseEntity<Employee> updateEmploye(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.save(employee);
        return ResponseEntity.ok(savedEmployee);
    }

    @PatchMapping("/employees/{id}")
    public ResponseEntity<Employee> patchEmploye(@RequestBody Map<String, Object> patchPayload,
                                                 @PathVariable int id) {
        Employee tempEmployee = employeeService.findById(id);

        // throw exception if null
        if (tempEmployee == null) {
            throw new RuntimeException("Employee id not found - " + id);
        }

        // throw exception if request body contains a "id" key
        if (patchPayload.containsKey("id")) {
            throw new RuntimeException("Employee id not allowed in request body - " + id);
        }

        Employee patchedEmployee = jsonMapper.updateValue(tempEmployee, patchPayload);

        employeeService.save(patchedEmployee);

        return ResponseEntity.ok(patchedEmployee);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable int id) {
        Employee employee = employeeService.findById(id);

        employeeService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
