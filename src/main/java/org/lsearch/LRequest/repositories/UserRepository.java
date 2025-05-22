package org.lsearch.LRequest.repositories;

import lombok.RequiredArgsConstructor;
import org.lsearch.LRequest.models.User;
import org.lsearch.LRequest.rowMappers.UserRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public int createUser(User user) {
        String sql = "INSERT INTO \"users\" (\"providerId\", \"name\",  \"email\", \"role\", \"createdAt\", \"updatedAt\") VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(
                sql,
                user.getProviderId(),
                user.getName(),
                user.getEmail(),
                user.getRole().toString(),
                user.getCreatedAt(),
                user.getUpdatedAt()
            );
    }

    public int updateUser(int id, String name, String email) {
        String sql = "UPDATE \"users\" SET ";
        if (name != null) {
            sql += ("\"name\" = ?, " + (email == null ? "" : "\"email\" = ?, "));
        } else {
            sql += "\"email\" = ?, ";
        }
        sql += "\"updatedAt\" = ? WHERE \"id\" = ?";
        System.out.println(sql);

        var now = Timestamp.valueOf(LocalDateTime.now());
        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                if (name != null) {
                    preparedStatement.setString(1, name);
                    if (email != null) {
                        preparedStatement.setString(2, email);
                        preparedStatement.setTimestamp(3, now);
                        preparedStatement.setInt(4, id);
                    } else {
                        preparedStatement.setTimestamp(2, now);
                        preparedStatement.setInt(3, id);
                    }
                } else {
                    preparedStatement.setString(1, email);
                    preparedStatement.setTimestamp(2, now);
                    preparedStatement.setInt(3, id);
                }
            }
        });
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
}
