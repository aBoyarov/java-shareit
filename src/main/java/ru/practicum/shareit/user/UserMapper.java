package ru.practicum.shareit.user;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Optional;

/**
 * @author Andrey Boyarov
 */
@Component
@RequiredArgsConstructor
public class UserMapper {

    private final UserRepository userRepository;
    public User toUser(UserDto userDto)  {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName() != null ? userDto.getName()
                        : userRepository.findById(userDto.getId()).get().getName())
                .email(userDto.getEmail() != null ? userDto.getEmail()
                        : userRepository.findById(userDto.getId()).get().getEmail())
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
