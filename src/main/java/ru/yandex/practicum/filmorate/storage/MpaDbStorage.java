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
public class MpaDbStorage implements MpaStorage {
    JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Mpa getById(int id) {
        Mpa currentMpa;
        try {
            currentMpa = jdbcTemplate.queryForObject("select * from mpa where mpa_id = ?", mpaRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Неверные параметры запроса рейтинга");
        }
        return currentMpa;
    }

    @Override
    public List<Mpa> getAll() {
        return jdbcTemplate.query("select * from mpa", mpaRowMapper());
    }

    private RowMapper<Mpa> mpaRowMapper() {
        return (rs, rowNum) -> new Mpa(
                rs.getInt("mpa_id"),
                rs.getString("name"));
    }
}
