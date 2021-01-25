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
    public ResponseEntity<?> getEmployee(@PathVariable long id) throws Exception {
        log.info("getEmployee id: {}", id);
        return ResponseEntity.ok(this.employeeService.getEmployee(id));
    }

    @PostMapping
    public ResponseEntity<?> saveEmployee(@RequestBody EmployeeDto employeeDto) throws Exception {
        log.info("saveEmployee employeeDto: {}", employeeDto);

        this.employeeService.saveEmployee(employeeDto);
        return ResponseEntity.created(null).build();
    }

}
