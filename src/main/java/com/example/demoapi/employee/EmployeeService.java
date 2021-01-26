package com.example.demoapi.employee;

import com.example.demoapi.exception.BadRequestException;
import com.example.demoapi.exception.ErrorCode;
import com.example.demoapi.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Employee employee = this.employeeRepository.getEmployeeById(id).orElseThrow(() -> new NotFoundException(ErrorCode.ERR_NOT_FOUND.code, "employee not found"));
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Transactional
    public void saveEmployee(EmployeeDto employeeDto) {
        this.employeeRepository.saveEmployee(modelMapper.map(employeeDto, Employee.class));
    }

    @Transactional
    public void updateEmployee(EmployeeDto employeeDto) {
        if (this.employeeRepository.updateEmployee(modelMapper.map(employeeDto, Employee.class)) == 0) {
            throw new BadRequestException(ErrorCode.ERR_PUT.code, "employee ID not found");
        }
    }

    @Transactional
    public void patchEmployee(EmployeeDto employeeDto) {
        if (this.employeeRepository.patchEmployee(modelMapper.map(employeeDto, Employee.class)) == 0) {
            throw new BadRequestException(ErrorCode.ERR_PATCH.code, "employee ID not found");
        }
    }
}
