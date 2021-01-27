package com.example.demoapi.repository;

import com.example.demoapi.employee.Employee;
import com.example.demoapi.entity.UserAccount;
import com.example.demoapi.model.LoginWithUsernameAndPasswordRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserAccountRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Optional<UserAccount> loginWithUsernameAndPassword(LoginWithUsernameAndPasswordRequest request) {
        String sql = "SELECT * FROM TBL_USER_ACCOUNT WHERE USERNAME = :username AND USER_PASSWORD = :password;";
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("username", request.getUsername());
            parameters.addValue("password", request.getPassword());
            return this.namedParameterJdbcTemplate.queryForObject(sql, parameters, new RowMapper<Optional<UserAccount>>() {
                @Override
                public Optional<UserAccount> mapRow(ResultSet resultSet, int i) throws SQLException {
                    return Optional.of(UserAccount.builder()
                            .id(resultSet.getLong("ID"))
                            .username(resultSet.getString("USERNAME"))
                            .displayName(resultSet.getString("DISPLAY_NAME"))
                            .email(resultSet.getString("EMAIL"))
                            .loginFailedCount(resultSet.getInt("LOGIN_FAILED_COUNT"))
                            .build());
                }
            });
        } catch (
                EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<UserAccount> findUserAccountByUsername(String username) {
        String sql = "SELECT * FROM TBL_USER_ACCOUNT WHERE USERNAME = :username AND DEACTIVATED = 'N';";

        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("username", username);

            return this.namedParameterJdbcTemplate.queryForObject(sql, parameters, new RowMapper<Optional<UserAccount>>() {
                @Override
                public Optional<UserAccount> mapRow(ResultSet resultSet, int i) throws SQLException {
                    return Optional.of(UserAccount.builder()
                            .id(resultSet.getLong("ID"))
                            .username(resultSet.getString("USERNAME"))
                            .displayName(resultSet.getString("DISPLAY_NAME"))
                            .email(resultSet.getString("EMAIL"))
                            .loginFailedCount(resultSet.getInt("LOGIN_FAILED_COUNT"))
                            .build());
                }
            });
        } catch (
                EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public int setLoginFailedCount(String username, Integer loginFailedCount) {

        String sql = "UPDATE TBL_USER_ACCOUNT SET LOGIN_FAILED_COUNT = :login_failed_count WHERE USERNAME = :username AND DEACTIVATED = 'N';";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("username", username);
        parameters.addValue("login_failed_count", loginFailedCount);
        return this.namedParameterJdbcTemplate.update(sql, parameters);
    }


}
