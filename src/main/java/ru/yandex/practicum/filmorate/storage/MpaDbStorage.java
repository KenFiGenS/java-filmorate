package ru.yandex.practicum.filmorate.storage;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.MpaType;

import java.util.List;
import java.util.Optional;

@Component
public class MpaDbStorage extends BaseStorage<Mpa> {
    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Mpa create(Mpa data) {
        return null;
    }

    @Override
    public Mpa upDate(Mpa data) {
        return null;
    }

    public Optional<Mpa> getById(int id) {
        Mpa currentMpa;
        try {
            currentMpa = jdbcTemplate.queryForObject("select * from mpa where mpa_id = ?", mpaRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Неверные параметры запроса рейтинга");
        }
        return Optional.ofNullable(currentMpa);
    }

    public List<Mpa> getAll() {
        return jdbcTemplate.query("select * from mpa", mpaRowMapper());
    }

    private RowMapper<Mpa> mpaRowMapper() {
        return (rs, rowNum) -> new Mpa(
                rs.getInt("mpa_id"),
                MpaType.valueOf(rs.getString("name")));
    }
}
