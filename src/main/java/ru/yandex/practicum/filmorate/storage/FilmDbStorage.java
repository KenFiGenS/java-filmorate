package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Component
public class FilmDbStorage extends BaseStorage<Film> {

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }


    @Override
    public Film create(Film data) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("film")
                .usingGeneratedKeyColumns("film_id");
        System.out.println(data.getReleaseDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        Map<String, String> params = Map.of("name", data.getName(),
                "description", data.getDescription(),
                "releaseDate", String.valueOf(data.getReleaseDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()),
                "duration", String.valueOf(data.getDuration()),
                "genre_id", String.valueOf(data.getGenreId()),
                "mpa_id", String.valueOf(data.getMpa_id()));
        Number id = simpleJdbcInsert.executeAndReturnKey(params);
        data.setId(id.intValue());
        return data;
    }

    @Override
    public Film upDate(Film data) {
        return null;
    }

    @Override
    public List<Film> getAll() {
        return jdbcTemplate.query("select * from film", filmRowMapper());
    }

    @Override
    public Optional<Film> getById(int id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select * from film f where f.film_id = ?",
                filmRowMapper(),
                id));
    }

    private RowMapper<Film> filmRowMapper(){
        return (rs, rowNum) -> new Film(
                rs.getInt("film_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("releaseDate"),
                rs.getInt("duration"),
                rs.getString("genre_id"),
                rs.getString("mpa_id"));
    }
}