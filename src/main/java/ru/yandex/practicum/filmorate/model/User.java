package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class User extends BaseUnit {
    @Email
    @NotEmpty
    private String email;
    @NotNull
    @NotBlank
    @Pattern(regexp = "^\\S*$", message = "В логине не может быть пробелов")
    private String login;
    private String name;
    @NotNull
    @PastOrPresent
    private LocalDate birthday;
    private List<User> friends = new ArrayList<>();
}
