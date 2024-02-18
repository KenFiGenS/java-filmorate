package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Data
@Valid
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Mpa extends BaseUnit{

    public Mpa(int id) {
        this.id = id;
    }
}
