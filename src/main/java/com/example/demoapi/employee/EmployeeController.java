package com.example.demoapi.employee;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<?> getEmployees() {
        return ResponseEntity.ok(this.employeeService.getEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable long id) {
        log.info("getEmployee id: {}", id);
        return ResponseEntity.ok(this.employeeService.getEmployee(id));
    }

    @PostMapping
    public ResponseEntity<?> saveEmployee(@RequestBody EmployeeDto employeeDto) {
        log.info("saveEmployee employeeDto: {}", employeeDto);

        this.employeeService.saveEmployee(employeeDto);
        return ResponseEntity.created(null).build();
    }

    @PutMapping
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeDto employeeDto) {
        log.info("updateEmployee employeeDto: {}", employeeDto);

        this.employeeService.updateEmployee(employeeDto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<?> patchEmployee(@RequestBody EmployeeDto employeeDto) {
        log.info("patchEmployee employeeDto: {}", employeeDto);

        this.employeeService.patchEmployee(employeeDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteEmployee(@RequestBody EmployeeDto employeeDto) {
        log.info("deleteEmployee employeeDto: {}", employeeDto);

        this.employeeService.deleteEmployee(employeeDto);
        return ResponseEntity.noContent().build();
    }

}
