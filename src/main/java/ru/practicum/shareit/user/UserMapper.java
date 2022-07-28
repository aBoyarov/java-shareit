package ru.practicum.shareit.user;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserStorage;

/**
 * @author Andrey Boyarov
 */
@Component
@RequiredArgsConstructor
public class UserMapper {

    private final UserStorage userStorage;
    public User toUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName() != null ? userDto.getName() : userStorage.getById(userDto.getId()).getName())
                .email(userDto.getEmail() != null ? userDto.getEmail() : userStorage.getById(userDto.getId()).getEmail())
                .build();
    }

    public UserDto toUserDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
