package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;

@Data
@Valid
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class Film extends BaseUnit {
    @NotBlank
    @Size(max = 55)
    private String name;
    @NotNull
    @Size(max = 55)
    private String description;
    @NotNull
    private Date releaseDate;
    @Min(1)
    private int duration;
    private HashSet<Genre> genres;
    @NotNull
    private Mpa mpa;

    public Film(int id, String name, String description, Date releaseDate, int duration, Mpa mpa) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
        this.genres = new LinkedHashSet<>();
    }
}
