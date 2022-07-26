package shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import shareit.exception.UserNotFoundException;
import shareit.user.dto.UserDto;
import shareit.user.model.User;
import shareit.user.repository.UserRepository;

import java.util.List;


/**
 * @author Andrey Boyarov
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Override
    public User create(UserDto userDto) {
        return userRepository.save(modelMapper.map(userDto, User.class));
    }

    @Override
    public User getById(long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User update(UserDto userDto, Long userId) throws UserNotFoundException {
        User user = modelMapper.map(getById(userId), User.class);
        modelMapper.map(userDto, user);
        return userRepository.save(user);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }
}
