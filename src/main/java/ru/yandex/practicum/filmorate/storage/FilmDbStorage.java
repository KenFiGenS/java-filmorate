package ru.yandex.practicum.filmorate.storage;

import lombok.SneakyThrows;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;


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
        if (data.getGenres() != null) {
            addGenresOnFilm(data);
        }
        return data;
    }

    @Override
    public Film upDate(Film data) {
        int count = jdbcTemplate.update("update films set" +
                        " name = ?," +
                        " description = ?," +
                        " releasedate = ?," +
                        " duration = ?," +
                        " mpa_id = ?" +
                        " where film_id = ?;", data.getName(), data.getDescription(), data.getReleaseDate(),
                data.getDuration(), data.getMpa().getId(), data.getId());
        if (count == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID " + data.getId() + " в базе данных не найден");
        }
        if (data.getGenres() != null) {
            jdbcTemplate.update("delete from film_genres where film_id = ?;", data.getId());
            addGenresOnFilm(data);
        }
        if (data.getGenres() != null && data.getGenres().isEmpty()) {
            jdbcTemplate.update("delete from film_genres where film_id = ?;", data.getId());
        }
        return getById(data.getId());
    }

    @Override
    @SneakyThrows
    public Film getById(int id) {
        Film currentFilm;
        try {
            currentFilm = jdbcTemplate.queryForObject("select f.film_id as film_id,\n" +
                    "f.name as film_name,\n" +
                    "f.description,\n" +
                    "f.releasedate,\n" +
                    "f.duration,\n" +
                    "f.mpa_id,\n" +
                    "m.name as mpa_name,\n" +
                    "fg.genre_id,\n" +
                    "g.name as genre_name \n" +
                    "from films f \n" +
                    "inner join mpa m on f.mpa_id = m.mpa_id\n" +
                    "LEFT OUTER JOIN film_genres fg on f.film_id = fg.film_id\n" +
                    "LEFT OUTER JOIN genre g on fg.genre_id = g.genre_id \n" +
                    "where f.film_id = ?;", filmRowMapperGetById(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID " + id + " в базе данных не найден");
        }
        return currentFilm;
    }

    @Override
    public List<Film> getAll() {
        List<Film> allFilm = jdbcTemplate.query("select f.film_id as film_id,\n" +
                "f.name as film_name,\n" +
                "f.description,\n" +
                "f.releasedate,\n" +
                "f.duration,\n" +
                "f.mpa_id,\n" +
                "m.name as mpa_name \n" +
                "from films f \n" +
                "inner join mpa m on f.mpa_id = m.mpa_id\n", filmRowMapperGetAll());

        List<String> genresData = getGenreDataString();
        List<Film> filmListWithGenres = getFilmWithGenresData(allFilm, genresData);
        return filmListWithGenres;
    }

    public List<Film> getMostPopularFilm(int count) {
        List<Film> allPopularFilm = jdbcTemplate.query("select f.film_id as film_id,\n" +
                "f.name as film_name,\n" +
                "f.description,\n" +
                "f.releasedate,\n" +
                "f.duration,\n" +
                "f.mpa_id,\n" +
                "m.name as mpa_name\n" +
                "from films f \n" +
                "inner join mpa m on f.mpa_id = m.mpa_id \n" +
                "LEFT OUTER JOIN likes l on f.film_id = l.film_id\n" +
                "group by f.film_id, f.name, f.description, f.duration, f.mpa_id, m.name, mpa_name\n" +
                "order by count(l.user_id) desc\n" +
                "limit ?;", filmRowMapperGetAll(), count);

        List<String> genresData = getGenreDataString();
        List<Film> filmListWithGenres = getFilmWithGenresData(allPopularFilm, genresData);
        return filmListWithGenres;
    }

    public void addLike(int id, int userId) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("likes");
        Map<String, Integer> data = Map.of("film_id", id,
                "user_id", userId);
        simpleJdbcInsert.execute(data);
    }

    public void removeLike(int id, int userId) {
        if (jdbcTemplate.update("DELETE FROM likes WHERE film_id = ? and user_id = ?;", id, userId) == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Неверные параметры запроса удаления лайков");
        }
    }

    private RowMapper<Film> filmRowMapperGetById() {
        RowMapper<Film> rowMapper = (rs, rowNum) -> {
            HashSet<Genre> currentGenreList = new LinkedHashSet<>();
            Film film = new Film(
                    rs.getInt("film_id"),
                    rs.getString("film_name"),
                    rs.getString("description"),
                    rs.getDate("releaseDate"),
                    rs.getInt("duration"),
                    new Mpa(rs.getInt("mpa_id"), rs.getString("mpa_name")));
            do {
                Genre currentGenre = new Genre(rs.getInt("genre_id"),
                        rs.getString("genre_name"));
                if (currentGenre.getId() != 0){
                    currentGenreList.add(currentGenre);
                }
            } while (rs.next());
            film.setGenres(currentGenreList);
            return film;
        };
        return rowMapper;
    }

    private RowMapper<Film> filmRowMapperGetAll() {
        RowMapper<Film> rowMapper = (rs, rowNum) -> {
            HashSet<Genre> currentGenreList = new LinkedHashSet<>();
            Film film = new Film(
                    rs.getInt("film_id"),
                    rs.getString("film_name"),
                    rs.getString("description"),
                    rs.getDate("releaseDate"),
                    rs.getInt("duration"),
                    new Mpa(rs.getInt("mpa_id"), rs.getString("mpa_name")));
            film.setGenres(currentGenreList);
            return film;
        };
        return rowMapper;
    }

    private List<String> getGenreDataString() {
        return jdbcTemplate.query("select f.film_id as film_id,\n" +
                "fg.genre_id as genre_id,\n" +
                "g.name as genre_name\n" +
                "from films f\n" +
                "join film_genres fg on f.film_id = fg.film_id \n" +
                "join genre g on fg.genre_id = g.genre_id;", (rs, rowNum) -> {
            String currentLine = rs.getString("film_id") + " " +
                    rs.getString("genre_id") + " " +
                    rs.getString("genre_name");
            return currentLine;
        });
    }

    private List<Film> getFilmWithGenresData(List<Film> films, List<String> genresData) {
        if (genresData != null || !genresData.isEmpty()) {
            for (String line : genresData) {
                String[] dataLine = line.split(" ");
                int filmId = Integer.parseInt(dataLine[0]);
                int genreId = Integer.parseInt(dataLine[1]);
                String genreType = dataLine[2];
                for (Film currentFilm : films) {
                    if (currentFilm.getId() == filmId) {
                        currentFilm.getGenres().add(new Genre(genreId, genreType));
                    }
                }
            }
        }
        return films.stream().sorted(Comparator.comparing(Film::getId)).collect(Collectors.toList());
    }

    private void addGenresOnFilm(Film data) {
        jdbcTemplate.batchUpdate("insert into film_genres (film_id, genre_id) values (?, ?);",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        List<Genre> genres = new ArrayList<>();
                        data.getGenres().stream().forEach(f -> genres.add(f));
                        ps.setInt(1, data.getId());
                        ps.setInt(2, genres.get(i).getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return data.getGenres().size();
                    }
                });
    }
}