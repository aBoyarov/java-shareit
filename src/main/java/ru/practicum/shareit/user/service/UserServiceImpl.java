package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.exception.UserValidException;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserStorage;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Andrey Boyarov
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserStorage userStorage;

    private final UserMapper userMapper;

    @Override
    public UserDto create(UserDto userDto) throws UserValidException {
        validEmail(userDto);
        User user = userStorage.create(userMapper.toUser(userDto));
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto getById(long id) {
        return userMapper.toUserDto(userStorage.getById(id));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userStorage.getAllUsers().values().stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto update(UserDto userDto, Long userId) throws UserNotFoundException, UserValidException {
        if(!userStorage.getAllUsers().containsKey(userId)){
            throw new UserNotFoundException("User not found");
        }
        validEmail(userDto);
        userDto.setId(userId);
        User user = userMapper.toUser(userDto);
        userStorage.update(user);
        return userMapper.toUserDto(user);
    }

    @Override
    public void delete(long id) {
        userStorage.delete(id);
    }

    private void validEmail(UserDto userDto) throws UserValidException {
        if(userStorage.getAllUsers().values().stream()
                .anyMatch(u -> u.getEmail().equals(userDto.getEmail()))){
            throw new UserValidException("email already exists");
        }
    }
}
