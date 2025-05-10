package org.lsearch.LRequest.repositories;

import lombok.RequiredArgsConstructor;
import org.lsearch.LRequest.enums.UserRole;
import org.lsearch.LRequest.models.User;
import org.lsearch.LRequest.rowMappers.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public int createUser(String providerId, String name, String email, UserRole role) {
        String sql = "INSERT INTO \"users\" (\"providerId\", \"name\",  \"email\", \"role\") VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, providerId, name, email, role.toString());
    }

    public User getUserByProviderId(String providerId) {
        String sql = "SELECT * FROM \"users\" WHERE \"providerId\" = ?";
        List<User> list = jdbcTemplate.query(sql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, providerId);
            }
        }, new UserRowMapper());

        return list.isEmpty() ? null : list.getFirst();
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM \"users\" WHERE \"id\" = ?";
        List<User> list = jdbcTemplate.query(sql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, id);
            }
        }, new UserRowMapper());

        return list.isEmpty() ? null : list.getFirst();
    }
}
