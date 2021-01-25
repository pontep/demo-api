package com.example.demoapi.employee;

import com.example.demoapi.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public List<EmployeeDto> getEmployees() {
        return this.employeeRepository.getEmployees().stream()
                .map(employee -> modelMapper.map(employee, EmployeeDto.class))
                .collect(Collectors.toList());
    }

    public EmployeeDto getEmployee(long id) {
        Employee employee = this.employeeRepository.getEmployeeById(id).orElseThrow(() -> new NotFoundException("employee not found"));
        return modelMapper.map(employee, EmployeeDto.class);
    }

    public int saveEmployee(EmployeeDto employeeDto) {
        return this.employeeRepository.saveEmployee(modelMapper.map(employeeDto, Employee.class));
    }
}
