package ru.yandex.practicum.filmorate.storage;

import lombok.SneakyThrows;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.*;

import java.util.ArrayList;
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
        if (data.getGenres() != null){
            data.getGenres().stream().forEach(i ->
                    jdbcTemplate.update("insert into \"film_genres\" (\"film_id\", \"genre_id\") values (?, ?);",
                    data.getId(), i.getId()));
        }
        return data;
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
                    "where f.film_id = ?;", filmRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID " + id + " в базе данных не найден");
        }
        return Optional.ofNullable(currentFilm);
    }

    @Override
    public List<Film> getAll() {
        return jdbcTemplate.query("select f.film_id as film_id,\n" +
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
                "LEFT OUTER JOIN genre g on fg.\"genre_id\" = g.\"genre_id\"", filmRowMapper());
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
                "limit ?;", filmRowMapper(), count));
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

    private RowMapper<Film> filmRowMapper() {
        System.out.println("111111111111");
        RowMapper<Film> rowMapper = (rs, rowNum) -> {
            Film film = new Film(
                    rs.getInt("film_id"),
                    rs.getString("film_name"),
                    rs.getString("description"),
                    rs.getDate("releaseDate"),
                    rs.getInt("duration"),
                    new Mpa(rs.getInt("mpa_id"), MpaType.valueOf(rs.getString("mpa_name"))));
            System.out.println("MAPPER2");
            List<Genre> currentGenreList = new ArrayList<>();
            if (rs.next()) {
                do {
                    int genreId = rs.getInt("genre_id");
                    String nameGenre = rs.getString("genre_name");
                    if(nameGenre != null) {
                        Genre currentGenre = new Genre(genreId,
                                GenreType.valueOf(nameGenre));
                        currentGenreList.add(currentGenre);
                    }
                } while (rs.next());
            }
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