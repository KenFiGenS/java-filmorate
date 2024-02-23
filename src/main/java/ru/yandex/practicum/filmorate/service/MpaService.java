package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaDbStorage;

@Service
public class MpaService extends BaseService<Mpa> {

    public MpaService(MpaDbStorage mpaDbStorage) {
        super(mpaDbStorage);
    }
}
