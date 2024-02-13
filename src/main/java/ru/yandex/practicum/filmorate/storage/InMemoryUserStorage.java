package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class InMemoryUserStorage extends BaseStorage<User> {

    public InMemoryUserStorage(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Optional<User> getById(int id) {
        return jdbcTemplate.queryForObject("select * from \"user\" u where u.user_id = ?", (rs, rowNum) -> Optional.of(new User(
                rs.getInt("user_id"),
                rs.getString("email"),
                rs.getString("login"),
                rs.getString("name"),
                rs.getDate("birthday"))), id);
    }
}