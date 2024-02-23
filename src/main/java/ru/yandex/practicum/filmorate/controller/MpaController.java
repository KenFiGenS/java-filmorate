package ru.yandex.practicum.filmorate.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.BaseService;

import java.util.List;

@RestController
@RequestMapping("/mpa")
@Slf4j
public class MpaController extends BaseController<Mpa> {

    private final BaseService<Mpa> baseService;

    public MpaController(BaseService<Mpa> baseService) {
        super(baseService);
        this.baseService = baseService;
    }

    @SneakyThrows
    @GetMapping("/{id}")
    public Mpa getMpaById(@PathVariable int id) {
        log.info("Getting MPA by ID");
        return baseService.getById(id);
    }

    @SneakyThrows
    @GetMapping
    public List<Mpa> getAllMpa() {
        log.info("Getting all MPA");
        return baseService.getAll();
    }

    @Override
    public void validate(Mpa data) {
    }
}
