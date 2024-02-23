package ru.yandex.practicum.filmorate.storage;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Component
public class GenreDbStorage extends BaseStorage<Genre> {
    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Genre create(Genre data) {
        return null;
    }

    @Override
    public Genre upDate(Genre data) {
        return null;
    }

    @Override
    public List<Genre> getAll() {
        return jdbcTemplate.query("select * from genre", genreRowMapper());
    }

    @Override
    public Genre getById(int id) {
        Genre currentGenre;
        try {
            currentGenre = jdbcTemplate.queryForObject("select * from genre where genre_id = ?",
                    genreRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Неверные параметры запроса жанра");
        }
        return currentGenre;
    }

    private RowMapper<Genre> genreRowMapper() {
        return (rs, rowNum) -> new Genre(
                rs.getInt("genre_id"),
                rs.getString("name"));
    }
}
