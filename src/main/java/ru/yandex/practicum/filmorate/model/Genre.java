package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Data
@Valid
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Genre extends BaseUnit {
    private String name;

    public Genre(int id, GenreType name) {
        this.id = id;
        this.name = genreGetString(name);
    }

    private String genreGetString(GenreType name) {
        String genreName = "";
        switch (name) {
            case comedy:
                genreName = "Комедия";
                break;
            case drama:
                genreName = "Драма";
                break;
            case cartoon:
                genreName = "Мультфильм";
                break;
            case thriller:
                genreName = "Триллер";
                break;
            case documentary:
                genreName = "Документальный";
                break;
            case action:
                genreName = "Боевик";
                break;
        }
        return genreName;
    }
}
