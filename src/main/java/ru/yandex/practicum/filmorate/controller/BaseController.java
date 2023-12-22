package ru.yandex.practicum.filmorate.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.yandex.practicum.filmorate.model.BaseUnit;
import ru.yandex.practicum.filmorate.service.BaseService;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;

/**
 * @noinspection checkstyle:Regexp
 */


public abstract class BaseController<T extends BaseUnit> {

    @Autowired
    private Map<T, BaseController<T>> baseControllerMap;

    public BaseController(BaseService<T> baseService) {
        baseControllerMap.put(T, baseService);
    }

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

    @SneakyThrows
    public T getById(int id) {

        return baseService.getById(id);
    }

    @ExceptionHandler
    public ResponseEntity<String> handler(ConstraintViolationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    public abstract void validate(T data);
}
