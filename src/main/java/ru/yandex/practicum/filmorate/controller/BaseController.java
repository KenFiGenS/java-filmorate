package ru.yandex.practicum.filmorate.controller;

import lombok.SneakyThrows;
import ru.yandex.practicum.filmorate.model.BaseUnit;
import ru.yandex.practicum.filmorate.service.BaseService;

import java.util.List;

public abstract class BaseController<T extends BaseUnit> {

    private final BaseService<T> baseService;

    public BaseController(BaseService<T> baseService) {
        this.baseService = baseService;
    }

//    @SneakyThrows
//    public T create(T data) {
//        return baseService.create(data);
//    }
//
//    @SneakyThrows
//    public T upDate(T data) {
//        return baseService.upDate(data);
//    }

    @SneakyThrows
    public T getById(int id) {
        return baseService.getById(id);
    }

    @SneakyThrows
    public List<T> getAll() {
        return baseService.getAll();
    }

    public abstract void validate(T data);
}
