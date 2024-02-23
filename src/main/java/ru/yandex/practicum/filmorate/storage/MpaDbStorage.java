package ru.yandex.practicum.filmorate.storage;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

@Component
public class MpaDbStorage extends BaseStorage<Mpa> {
    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public Mpa getById(int id) {
        Mpa currentMpa;
        try {
            currentMpa = jdbcTemplate.queryForObject("select * from mpa where mpa_id = ?", mpaRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Неверные параметры запроса рейтинга");
        }
        return currentMpa;
    }

    public List<Mpa> getAll() {
        return jdbcTemplate.query("select * from mpa", mpaRowMapper());
    }

    private RowMapper<Mpa> mpaRowMapper() {
        return (rs, rowNum) -> new Mpa(
                rs.getInt("mpa_id"),
                rs.getString("name"));
    }

    @Override
    public Mpa create(Mpa data) {
        return null;
    }

    @Override
    public Mpa upDate(Mpa data) {
        return null;
    }

    @Override
    public void addFriend(int id, int friendId) {

    }

    @Override
    public void removeFriend(int id, int friendId) {

    }

    @Override
    public List<Mpa> getAllFriends(int id) {
        return null;
    }

    @Override
    public List<Mpa> getGeneralFriends(int id, int otherId) {
        return null;
    }
}
