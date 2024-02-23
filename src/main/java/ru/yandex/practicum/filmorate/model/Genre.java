package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
@Valid
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Genre extends BaseUnit {
    @NotBlank
    private String name;

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
