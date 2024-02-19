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
public class Mpa extends BaseUnit{
    MpaType name;
//    private  final Map<Integer, MpaType> mpaNames = Map.of(1, MpaType.G,
//            2, MpaType.PG,
//            3, MpaType.PG13,
//            4, MpaType.R,
//            5, MpaType.NC17);

    public Mpa(int id, MpaType name) {
        this.id = id;
        this.name = name;
    }

//    private MpaType getNameById(int id) {
//        return  mpaNames.keySet().stream().filter(i -> i == id)
//                .findFirst()
//                .map(i -> mpaNames.get(i))
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Рейтинга с id: " + id + " не существует"));
//    }
}
