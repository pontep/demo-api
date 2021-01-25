package com.example.demoapi.employee;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public List<EmployeeDto> getEmployees() {
        return this.employeeRepository.getEmployees().stream()
                .map(employee -> modelMapper.map(employee, EmployeeDto.class))
                .collect(Collectors.toList());
    }

    public EmployeeDto getEmployee(long id) throws Exception {
        return this.employeeRepository.getEmployeeById(id)
                .map(employee -> modelMapper.map(employee, EmployeeDto.class))
                .orElseThrow(() -> new Exception("not found"));
    }
}
