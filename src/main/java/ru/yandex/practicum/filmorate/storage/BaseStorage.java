package ru.yandex.practicum.filmorate.storage;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.BaseUnit;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
@AllArgsConstructor
public abstract class BaseStorage<T extends BaseUnit> {
    @Autowired
    protected final JdbcTemplate jdbcTemplate;


    public abstract T create(T data);


    public abstract T upDate(T data);


    public abstract List<T> getAll();

    public abstract Optional<T> getById(int id) throws Exception;

}
