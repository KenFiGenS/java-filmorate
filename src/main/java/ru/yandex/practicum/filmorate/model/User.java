package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;
import java.sql.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseUnit {
    @Email
    @NotEmpty
    @Size(max = 55)
    private String email;
    @NotNull
    @NotBlank
    @Pattern(regexp = "^\\S*$", message = "В логине не может быть пробелов")
    @Size(max = 55)
    private String login;
    @NotBlank
    @Size(max = 55)
    private String name;
    @NotNull
    @PastOrPresent
    private Date birthday;

    public User(int userId, String email, String login, String name, Date birthday) {
        this.id = userId;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}
