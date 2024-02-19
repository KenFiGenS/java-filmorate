package ru.yandex.practicum.filmorate.storage;

import lombok.SneakyThrows;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.MpaType;

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

        jdbcTemplate.update("insert into likes (film_id, user_id) values (?, null)", data.getId());

//        List<Genre> currentList = new ArrayList<>();
//        Map<String, Integer> currentGenresData = new HashMap<>();
//
//        if(data.getGenres()!= null){
//            data.getGenres().forEach(genre -> {
//                currentGenresData.put(String.valueOf(id), genre.getId());
//                currentList.add(new Genre(genre.getId()));
//            });
//            simpleJdbcInsert.withTableName("film_genres");
//            simpleJdbcInsert.execute(currentGenresData);
//        }
//        data.setGenres(currentList);
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
        return jdbcTemplate.query("select f.film_id,\n" +
                "f.\"name\",\n" +
                "f.description,\n" +
                "f.releasedate,\n" +
                "f.duration,\n" +
                "f.\"mpa_id\",\n" +
                "m.\"name\" as mpa_name\n" +
                "from films f \n" +
                "inner join mpa m on f.\"mpa_id\" = m.\"mpa_id\"", filmRowMapper());
    }

    public Optional<List<Film>> getMostPopularFilm(int count){
        return Optional.of(jdbcTemplate.query("select f.film_id,\n" +
                "f.\"name\",\n" +
                "f.description,\n" +
                "f.releasedate,\n" +
                "f.duration,\n" +
                "f.\"mpa_id\",\n" +
                "m.\"name\" as mpa_name,\n" +
                "count(l.user_id)\n" +
                "from films f \n" +
                "inner join mpa m on f.\"mpa_id\" = m.\"mpa_id\"\n" +
                "inner join likes l on f.film_id = l.film_id\n" +
                "group by f.film_id, f.\"name\", f.description, f.duration, f.\"mpa_id\", m.\"name\", mpa_name\n" +
                "order by count(l.user_id) desc\n" +
                "limit ?;", filmRowMapper(), count));
    }

    @Override
    @SneakyThrows
    public Optional<Film> getById(int id) {
        Film currentFilm;
        try {
            currentFilm = jdbcTemplate.queryForObject("select f.film_id,\n" +
                    "f.\"name\",\n" +
                    "f.description,\n" +
                    "f.releasedate,\n" +
                    "f.duration,\n" +
                    "f.\"mpa_id\",\n" +
                    "m.\"name\" as mpa_name\n" +
                    "from films f \n" +
                    "inner join mpa m on f.\"mpa_id\" = m.\"mpa_id\" \n" +
                    "where f.film_id = ?;", filmRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID " + id + " в базе данных не найден");
        }

        return Optional.ofNullable(currentFilm);
    }

    public void addLike(int id, int userId) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("likes");
        Map<String, Integer> data = Map.of("film_id", id,
                                           "user_id", userId);
        simpleJdbcInsert.execute(data);
    }

    public void removeLike(int id, int userId) {
        if( jdbcTemplate.update("DELETE FROM likes WHERE film_id = ? and user_id = ?;", id, userId) == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Неверные параметры запроса удаления лайков");
        }
    }

    private RowMapper<Film> filmRowMapper(){
        return (rs, rowNum) -> new Film(
                rs.getInt("film_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("releaseDate"),
                rs.getInt("duration"),
                new Mpa(rs.getInt("mpa_id"), MpaType.valueOf(rs.getString("mpa_name"))));
    }
}