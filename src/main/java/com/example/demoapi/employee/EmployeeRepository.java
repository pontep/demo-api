package com.example.demoapi.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmployeeRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Collection<Employee> getEmployees() {
        String sql = "SELECT * FROM TBL_EMPLOYEES WHERE IS_DELETED = 'N'";
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
        String sql = "SELECT * FROM TBL_EMPLOYEES WHERE ID = :id AND IS_DELETED = 'N';";

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

    public int saveEmployee(Employee employee) {
        String sql = "INSERT INTO TBL_EMPLOYEES (first_name, last_name, email) VALUES\n" +
                "  (:firstName, :lastName, :email);";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("firstName", employee.getFirstName());
        parameters.addValue("lastName", employee.getLastName());
        parameters.addValue("email", employee.getEmail());

        return this.namedParameterJdbcTemplate.update(sql, parameters);
    }

    public int updateEmployee(Employee employee) {
        String sql = "UPDATE TBL_EMPLOYEES SET first_name = :firstName, last_name = :lastName, email = :email\n" +
                "  WHERE ID = :id;";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", employee.getId());
        parameters.addValue("firstName", employee.getFirstName());
        parameters.addValue("lastName", employee.getLastName());
        parameters.addValue("email", employee.getEmail());

        return this.namedParameterJdbcTemplate.update(sql, parameters);
    }

    public int patchEmployee(Employee employee) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE TBL_EMPLOYEES SET ID = :id");

        if (StringUtils.hasText(employee.getFirstName())) {
            sql.append(", first_name = :firstName");
            parameters.addValue("firstName", employee.getFirstName());
        }
        if (StringUtils.hasText(employee.getLastName())) {
            sql.append(", last_name = :lastName");
            parameters.addValue("lastName", employee.getLastName());
        }
        if (StringUtils.hasText(employee.getEmail())) {
            sql.append(", email = :email");
            parameters.addValue("email", employee.getEmail());
        }

        sql.append(" WHERE ID = :id;");
        parameters.addValue("id", employee.getId());

        return this.namedParameterJdbcTemplate.update(sql.toString(), parameters);
    }

    public int deleteEmployee(Employee employee) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE TBL_EMPLOYEES SET IS_DELETED = 'Y' WHERE ID = :id;");
        parameters.addValue("id", employee.getId());

        return this.namedParameterJdbcTemplate.update(sql.toString(), parameters);
    }
}
