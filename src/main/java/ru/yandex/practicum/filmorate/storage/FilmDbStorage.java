package ru.yandex.practicum.filmorate.storage;

import lombok.SneakyThrows;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


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
//        jdbcTemplate.update("insert into likes (film_id, user_id) values (?, null)", data.getId());
        if (data.getGenres() != null){
            data.getGenres().stream().forEach(i ->
                    jdbcTemplate.update("insert into \"film_genres\" (\"film_id\", \"genre_id\") values (?, ?);",
                            data.getId(), i.getId()));
        }
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
        try {
            if (data.getGenres() != null){
                jdbcTemplate.update("delete from \"film_genres\" where film_id = ?;", data.getId());
                data.getGenres().stream().forEach(i ->
                        jdbcTemplate.update("insert into \"film_genres\" (\"film_id\", \"genre_id\") values (?, ?);",
                                data.getId(), i.getId()));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (data.getGenres() != null && data.getGenres().isEmpty()){
            jdbcTemplate.update("delete from \"film_genres\" where film_id = ?;", data.getId());
        }
        return getById(data.getId()).get();
    }

    @Override
    @SneakyThrows
    public Optional<Film> getById(int id) {
        Film currentFilm;
        try {
            currentFilm = jdbcTemplate.queryForObject("select f.film_id as film_id,\n" +
                    "f.\"name\" as film_name,\n" +
                    "f.description,\n" +
                    "f.releasedate,\n" +
                    "f.duration,\n" +
                    "f.\"mpa_id\",\n" +
                    "m.\"name\" as mpa_name,\n" +
                    "fg.\"genre_id\",\n" +
                    "g.\"name\" as genre_name \n" +
                    "from films f \n" +
                    "inner join mpa m on f.\"mpa_id\" = m.\"mpa_id\"\n" +
                    "LEFT OUTER JOIN film_genres fg on f.film_id = fg.film_id\n" +
                    "LEFT OUTER JOIN genre g on fg.\"genre_id\" = g.\"genre_id\" \n" +
                    "where f.film_id = ?;", filmRowMapperGetById(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID " + id + " в базе данных не найден");
        }
        return Optional.ofNullable(currentFilm);
    }

    @Override
    public List<Film> getAll() {
        List<Film> allFilm = jdbcTemplate.query("select f.film_id as film_id,\n" +
                "f.\"name\" as film_name,\n" +
                "f.description,\n" +
                "f.releasedate,\n" +
                "f.duration,\n" +
                "f.\"mpa_id\",\n" +
                "m.\"name\" as mpa_name \n" +
                "from films f \n" +
                "inner join mpa m on f.\"mpa_id\" = m.\"mpa_id\"\n", filmRowMapperGetAll());
        System.out.println(allFilm.size());
        List<Film> resultList = new ArrayList<>();
        for (int i = allFilm.size() - 1; i >= 0; i--) {
            Film currentFilm = allFilm.get(i);
            int id = currentFilm.getId();
            System.out.println(id);
            List<Genre> currentGenresList = jdbcTemplate.query("select fg.\"genre_id\",\n" +
                    "g.\"name\" as genre_name\n" +
                    "from films f\n" +
                    "join film_genres fg on f.film_id = fg.film_id \n" +
                    "join genre g on fg.genre_id = g.genre_id\n" +
                    "where f.film_id = ?;", (rs, rowNum) -> new Genre(rs.getInt("genre_id"),
                            GenreType.valueOf(rs.getString("genre_name"))), id);
            currentFilm.setGenres(currentGenresList);
            resultList.add(currentFilm);
        }
        return resultList;
    }

    public Optional<List<Film>> getMostPopularFilm(int count){
        return Optional.of(jdbcTemplate.query("select f.film_id,\n" +
                "f.\"name\" as film_name,\n" +
                "f.description,\n" +
                "f.releasedate,\n" +
                "f.duration,\n" +
                "f.\"mpa_id\",\n" +
                "m.\"name\" as mpa_name,\n" +
                "fg.\"genre_id\",\n" +
                "g.\"name\" as genre_name\n" +
                "from films f \n" +
                "inner join mpa m on f.\"mpa_id\" = m.\"mpa_id\"\n" +
                "LEFT OUTER JOIN film_genres fg on f.film_id = fg.film_id\n" +
                "LEFT OUTER JOIN genre g on fg.\"genre_id\" = g.\"genre_id\" \n" +
                "LEFT OUTER JOIN likes l on f.film_id = l.film_id\n" +
                "group by f.film_id, f.\"name\", f.description, f.duration, f.\"mpa_id\", m.\"name\", mpa_name, fg.\"genre_id\", g.\"name\"\n" +
                "order by count(l.user_id) desc\n" +
                "limit ?;", filmRowMapperGetById(), count));
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

    public Optional<Mpa> getMpaById(int id) {
        Mpa currentMpa;
        try {
            currentMpa = jdbcTemplate.queryForObject("select * from mpa where mpa_id = ?", mpaRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Неверные параметры запроса рейтинга");
        }
        return Optional.ofNullable(currentMpa);
    }

    public List<Mpa> getAllMpa() {
        return jdbcTemplate.query("select * from mpa", mpaRowMapper());
    }

    public List<Genre> getAllGenres() {
        return jdbcTemplate.query("select * from genre", genreRowMapper());
    }

    private RowMapper<Film> filmRowMapperGetById() {
        RowMapper<Film> rowMapper = (rs, rowNum) -> {
            List<Genre> currentGenreList = new ArrayList<>();
            Film film = new Film(
                    rs.getInt("film_id"),
                    rs.getString("film_name"),
                    rs.getString("description"),
                    rs.getDate("releaseDate"),
                    rs.getInt("duration"),
                    new Mpa(rs.getInt("mpa_id"), MpaType.valueOf(rs.getString("mpa_name"))));
            do {
                try {
                    Genre currentGenre = new Genre(rs.getInt("genre_id"),
                            GenreType.valueOf(rs.getString("genre_name")));
                    currentGenreList.add(currentGenre);
                } catch (NullPointerException e) {
                    break;
                }
            } while (rs.next());
            film.setGenres(currentGenreList);
            return film;
        };
        return rowMapper;
    }

    private RowMapper<Film> filmRowMapperGetAll() {
        RowMapper<Film> rowMapper = (rs, rowNum) -> {
            List<Genre> currentGenreList = new ArrayList<>();
            Film film = new Film(
                    rs.getInt("film_id"),
                    rs.getString("film_name"),
                    rs.getString("description"),
                    rs.getDate("releaseDate"),
                    rs.getInt("duration"),
                    new Mpa(rs.getInt("mpa_id"), MpaType.valueOf(rs.getString("mpa_name"))));
            film.setGenres(currentGenreList);
            return film;
        };
        return rowMapper;
    }

    public Optional<Genre> getGenreById(int id) {
        Genre currentGenre;
        try {
            currentGenre = jdbcTemplate.queryForObject("select * from genre where genre_id = ?",
                    genreRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Неверные параметры запроса жанра");
        }
        return Optional.ofNullable(currentGenre);
    }

    private RowMapper<Mpa> mpaRowMapper() {
        return (rs, rowNum) -> new Mpa(
                rs.getInt("mpa_id"),
                MpaType.valueOf(rs.getString("name")));
    }

    private RowMapper<Genre> genreRowMapper() {
        return (rs, rowNum) -> new Genre(
                rs.getInt("genre_id"),
                GenreType.valueOf(rs.getString("name")));
    }


}