package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Data
@Valid
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Mpa extends BaseUnit {
    String name;

    public Mpa(int id, MpaType name) {
        this.id = id;
        this.name = genNpaString(name);
    }

    private String genNpaString(MpaType name) {
        String mpaName = "";
        switch (name) {
            case G:
                mpaName = "G";
                break;
            case PG:
                mpaName = "PG";
                break;
            case PG13:
                mpaName = "PG-13";
                break;
            case R:
                mpaName = "R";
                break;
            case NC17:
                mpaName = "NC-17";
                break;
        }
        return mpaName;
    }
}
