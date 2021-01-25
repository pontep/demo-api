package com.example.demoapi.employee;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class EmployeeRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Collection<Employee> getEmployees() {
        String sql = "SELECT * FROM TBL_EMPLOYEES";
        return this.namedParameterJdbcTemplate.query(sql, new RowMapper<Employee>() {
            @Override
            public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
                return Employee.builder()
                        .id(resultSet.getLong("ID"))
                        .firstName(resultSet.getString("FIRST_NAME"))
                        .lastName(resultSet.getString("LAST_NAME"))
                        .email(resultSet.getString("EMAIL"))
                        .build();
            }
        });
    }

    public Optional<Employee> getEmployeeById(long id) {
        String sql = "SELECT * FROM TBL_EMPLOYEES WHERE ID = :id";

        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("id", id);

            return this.namedParameterJdbcTemplate.queryForObject(sql, parameters, new RowMapper<Optional<Employee>>() {
                @Override
                public Optional<Employee> mapRow(ResultSet resultSet, int i) throws SQLException {
                    return Optional.of(Employee.builder()
                            .id(resultSet.getLong("ID"))
                            .firstName(resultSet.getString("FIRST_NAME"))
                            .lastName(resultSet.getString("LAST_NAME"))
                            .email(resultSet.getString("EMAIL"))
                            .build());
                }
            });
        } catch (
                EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
