package ru.yandex.practicum.filmorate.controller;

import lombok.SneakyThrows;
import ru.yandex.practicum.filmorate.controllerException.ValidationException;
import ru.yandex.practicum.filmorate.model.BaseUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseController <T extends BaseUnit> {

    private final Map<Integer, T> storage = new HashMap<>();

    private int generatedId;

    @SneakyThrows
    public T create(T data) {
        if (storage.containsKey(data.getId())) {
            throw new ValidationException(String.format("The data %s has already been creating", data));
        }
        validate(data);
        data.setId(++generatedId);
        storage.put(data.getId(), data);
        return data;
    }

    @SneakyThrows
    public T upDate(T data){
        if (!storage.containsKey(data.getId())) {
            throw new ValidationException(String.format("Data %s not found", data));
        }
        validate(data);
        storage.put(data.getId(), data);
        return data;
    }

    @SneakyThrows
    public List<T> getAll() {
        if (storage.size() == 0) {
            throw new ValidationException("No data available");
        }
        return new ArrayList<>(storage.values());
    }

    public abstract void validate(T data);
}
