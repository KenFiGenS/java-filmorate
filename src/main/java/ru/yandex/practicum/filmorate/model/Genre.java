package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Map;

@Data
@Valid
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Genre extends BaseUnit{
    private GenreType name;
//    private  final Map<Integer, GenreType> genres = Map.of(1, GenreType.ACTION,
//            2, GenreType.COMEDY,
//            3, GenreType.DOCUMENTARY,
//            4, GenreType.DORAMA,
//            5, GenreType.DRAMA,
//            6, GenreType.FANTASTIC,
//            7, GenreType.FANTASY,
//            8, GenreType.MILITARY,
//            9, GenreType.PARODY);

    public Genre(int id, GenreType name){
        this.id = id;
        this.name = name;
    }

//    private GenreType getNameById(int id) {
//        return  genres.keySet().stream().filter(i -> i == id)
//                .findFirst()
//                .map(i -> genres.get(i))
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Жанра с id: " + id + " не существует"));
//    }
}
