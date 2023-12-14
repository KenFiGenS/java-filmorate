package ru.yandex.practicum.filmorate.storage;

import lombok.SneakyThrows;
import ru.yandex.practicum.filmorate.controllerException.ValidationException;
import ru.yandex.practicum.filmorate.model.BaseUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @noinspection checkstyle:Regexp
 */

public abstract class BaseStorage<T extends BaseUnit> {

    private final Map<Integer, T> storage = new HashMap<>();
    private int generatedId;

    public Map<Integer, T> getStorage() {
        return storage;
    }

    public int getGeneratedId() {
        return generatedId;
    }

    @SneakyThrows
    public T create(T data) {
        for (T data1 : storage.values()) {
            if (data1.equals(data)) {
                throw new ValidationException(String.format("The data %s has already been creating", data));
            }
        }

        data.setId(++generatedId);
        storage.put(data.getId(), data);
        return data;
    }

    @SneakyThrows
    public T upDate(T data) {
        if (!storage.containsKey(data.getId())) {
            throw new ValidationException(String.format("Data %s not found", data));
        }
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

    public T getById(int id) {
        if (storage.containsKey(id)) {
            return storage.get(id);
        } else {
            throw new IllegalArgumentException("This user has not been created yet");
        }
    }
}
