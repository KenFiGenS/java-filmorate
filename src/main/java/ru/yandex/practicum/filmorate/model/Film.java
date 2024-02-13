package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Validated
public class Film extends BaseUnit {
    @NotBlank
    @Size(max = 55)
    private String name;
    @NotNull
    @Size(max = 255)
    private String description;
    @NotNull
    private Date releaseDate;
    @Min(1)
    private int duration;
    @NotNull
    private String genreId;
    @NotNull
    private String mpa_id;

    public Film(int id, String name, String description, Date releaseDate, int duration, String genreId, String mpa_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.genreId = genreId;
        this.mpa_id = mpa_id;
    }
}
