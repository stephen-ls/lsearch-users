package org.lsearch.LRequest.rowMappers;

import org.lsearch.LRequest.enums.UserRole;
import org.lsearch.LRequest.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setProviderId(rs.getString("providerId"));
        user.setEmail(rs.getString("email"));
        user.setName(rs.getString("name"));
        user.setRole(UserRole.valueOf(rs.getString("role")));
        user.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
        user.setUpdatedAt(rs.getTimestamp("updatedAt").toLocalDateTime());
        return user;
    }
}
