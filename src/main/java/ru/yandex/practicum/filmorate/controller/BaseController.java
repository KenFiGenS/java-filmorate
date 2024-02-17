package ru.yandex.practicum.filmorate.controller;

import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.yandex.practicum.filmorate.model.BaseUnit;
import ru.yandex.practicum.filmorate.service.BaseService;

import javax.validation.ConstraintViolationException;
import java.util.List;

public abstract class BaseController<T extends BaseUnit> {

    private final BaseService<T> baseService;

    public BaseController(BaseService<T> baseService) {
        this.baseService = baseService;
    }

    @SneakyThrows
    public T getById(int id) {

        return baseService.getById(id);
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

    @ExceptionHandler
    public ResponseEntity<String> handler(ConstraintViolationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    public abstract void validate(T data);
}
