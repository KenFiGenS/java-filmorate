package ru.yandex.practicum.filmorate.storage;

import lombok.SneakyThrows;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;

@Component
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addFriend(int id, int friendId) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("friendship_list");
        Map<String, String> params = Map.of("user1_id", String.valueOf(id),
                "user2_id", String.valueOf(friendId));
        try {
            simpleJdbcInsert.execute(params);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public User create(User data) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");
        Map<String, String> params = Map.of("login", data.getLogin(),
                "name", data.getName(),
                "email", data.getEmail(),
                "birthday", data.getBirthday().toString());
        Number id = simpleJdbcInsert.executeAndReturnKey(params);
        data.setId(id.intValue());
        return data;
    }

    @Override
    @SneakyThrows
    public User upDate(User data) {
        int count = jdbcTemplate.update("update users set" +
                " login = ?," +
                " name = ?," +
                " email = ?," +
                " birthday = ?" +
                " where user_id = ?;", data.getLogin(), data.getName(), data.getEmail(), data.getBirthday(), data.getId());
        if (count == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID " + data.getId() + " в базе данных не найден");
        }
        return data;
    }

    @Override
    @SneakyThrows
    public List<User> getAll() {
        return jdbcTemplate.query("select * from users", userRowMapper());
    }

    @Override
    @SneakyThrows
    public User getById(int id) {
        try {
            return jdbcTemplate.queryForObject("select * from users u where u.user_id = ?",
                    userRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This user has not been created yet.");
        }
    }

    @SneakyThrows
    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> new User(
                rs.getInt("user_id"),
                rs.getString("login"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getDate("birthday"));
    }

    @SneakyThrows
    public List<User> getGeneralFriends(int id, int otherId) {
        try {
            return jdbcTemplate.query("select u.user_id, u.login, u.name, u.email, u.birthday " +
                    "from users u " +
                    "inner join friendship_list fl on fl.user2_id = u.user_id " +
                    "where fl.user1_id = ? " +
                    "or fl.user1_id  = ? " +
                    "group by user_id " +
                    "having count(fl.user2_id) > 1;", userRowMapper(), id, otherId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.OK, "Список друзей пуст");
        }
    }

    public List<User> getAllFriends(int id) {
        try {
            return jdbcTemplate.query("select *" +
                            "from users u " +
                            "where u.user_id in (select fl.user2_id " +
                            "from friendship_list fl " +
                            "where fl.user1_id = ?);",
                    userRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.OK, "Список друзей пуст");
        }
    }

    public void removeFriend(int id, int friendId) {
        jdbcTemplate.update("DELETE FROM friendship_list WHERE user1_id = ? and user2_id = ?;", id, friendId);
    }
}