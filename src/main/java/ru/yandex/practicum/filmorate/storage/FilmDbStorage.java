package ru.yandex.practicum.filmorate.storage;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.ZoneId;
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
                .withTableName("films")
                .usingGeneratedKeyColumns("film_id");
        Map<String, Object> params = Map.of("name", data.getName(),
                "description", data.getDescription(),
                "releasedate", data.getReleaseDate(),
                "duration", String.valueOf(data.getDuration()),
                "mpa_id", String.valueOf(data.getMpa().getId()));
        Number id = simpleJdbcInsert.executeAndReturnKey(params);
        data.setId(id.intValue());
        return data;
    }

    @Override
    public Film upDate(Film data) {
        int count  = jdbcTemplate.update("update films set" +
                " \"name\" = ?," +
                " description = ?," +
                " releasedate = ?," +
                " duration = ?," +
                "\"mpa_id\" = ?" +
                " where film_id = ?;",  data.getName(), data.getDescription(), data.getReleaseDate(),
                data.getDuration(), data.getMpa().getId(), data.getId());
        if(count == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID " + data.getId() + " в базе данных не найден");
        }
        return data;
    }

    @Override
    public List<Film> getAll() {
        return jdbcTemplate.query("select * from films", filmRowMapper());
    }

    @Override
    public Optional<Film> getById(int id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select * from films f where f.film_id = ?",
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
                new Mpa(rs.getInt("mpa_id")));
    }
}