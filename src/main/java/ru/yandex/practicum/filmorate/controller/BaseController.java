package ru.yandex.practicum.filmorate.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.model.BaseUnit;
import ru.yandex.practicum.filmorate.service.BaseService;

import java.util.List;

/**
 * @noinspection checkstyle:Regexp
 */


public abstract class BaseController<T extends BaseUnit> {
    @Autowired
    BaseService<T> baseService;

    @SneakyThrows
    public T create(T data) {

        return baseService.create(data);
    }

    @SneakyThrows
    public T upDate(T data) {

        return baseService.upDate(data);
    }

    @SneakyThrows
    public List<T> getAll() {

        return baseService.getAll();
    }

    public abstract void validate(T data);
}
