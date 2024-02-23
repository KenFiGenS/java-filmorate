package ru.yandex.practicum.filmorate.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.BaseStorage;
import ru.yandex.practicum.filmorate.storage.MpaDbStorage;

import java.util.List;

@Service
public class MpaService extends BaseService<Mpa>{
    MpaDbStorage mpaDbStorage;

    public MpaService(MpaDbStorage mpaDbStorage) {
        super(mpaDbStorage);
        this.mpaDbStorage = mpaDbStorage;
    }

    public Mpa getMpaById(int id) {
        return mpaDbStorage.getById(id);
    }

    public List<Mpa> getAllMpa() {
        return mpaDbStorage.getAll();
    }
}