package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.sql.Date;

@Data
@Valid
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class User extends BaseUnit {

    @NotNull
    @NotBlank
    @Pattern(regexp = "^\\S*$", message = "В логине не может быть пробелов")
    @Size(max = 55)
    private String login;
    @Size(max = 55)
    private String name;
    @Email
    @NotEmpty
    @Size(max = 55)
    private String email;
    @NotNull
    @PastOrPresent
    private Date birthday;

    public User(int id, String login, String name, String email, Date birthday) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
    }
}
