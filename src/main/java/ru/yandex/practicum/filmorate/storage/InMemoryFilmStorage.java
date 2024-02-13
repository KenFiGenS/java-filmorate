package ru.yandex.practicum.filmorate.storage;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Component
public class InMemoryFilmStorage extends BaseStorage<Film> {

    public InMemoryFilmStorage(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }


    @Override
    public Optional<Film> getById(int id) {
        return jdbcTemplate.queryForObject("select * from film f where f.film_id = ?", (rs, rowNum) -> Optional.of(new Film(
                rs.getInt("film_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("releaseDate"),
                rs.getInt("duration"),
                rs.getString("genre_id"),
                rs.getString("mpa_id"))), id);
    }
}