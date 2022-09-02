package ru.practicum.shareit.user.dto;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * @author Andrey Boyarov
 */
@Data
public class UserDto {
    private Long id;
    @NotEmpty
    private String name;
    @Email
    @NotEmpty
    private String email;
}
