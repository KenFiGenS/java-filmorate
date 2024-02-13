package ru.yandex.practicum.filmorate.storage;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.controllerException.ValidationException;
import ru.yandex.practicum.filmorate.model.BaseUnit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
@AllArgsConstructor
public abstract class BaseStorage<T extends BaseUnit> {

    protected final JdbcTemplate jdbcTemplate;

//    @SneakyThrows
//    public abstract T create(T data);
//
//    @SneakyThrows
//    public abstract T upDate(T data);
//
//    @SneakyThrows
//    public abstract List<T> getAll();

    public abstract Optional<T> getById(int id);

}
