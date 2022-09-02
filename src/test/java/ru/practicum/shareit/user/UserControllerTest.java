package ru.practicum.shareit.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Andrey Boyarov
 */
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ModelMapper modelMapper;

    User user = new User(
            1L,
            "Alexey",
            "Alexey@yandex.ru");

    UserDto userDto = new UserDto(
            1L,
            "Ivan",
            "Ivanov@yandex.ru");


    @Test
    void create() throws Exception {
        when(userService.create(any()))
                .thenReturn(user);
        mvc.perform(post("/users")
                        .content(mapper.writeValueAsString(userDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(modelMapper.map(user, UserDto.class).getId()), Long.class))
                .andExpect(jsonPath("$.name", is(modelMapper.map(user, UserDto.class).getName())))
                .andExpect(jsonPath("$.email", is(modelMapper.map(user, UserDto.class).getEmail())));
    }

    @Test
    void getUserById() throws Exception {
        when(userService.getById(anyLong()))
                .thenReturn(user);
        mvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(modelMapper.map(user, UserDto.class).getId()), Long.class))
                .andExpect(jsonPath("$.name", is(modelMapper.map(user, UserDto.class).getName())))
                .andExpect(jsonPath("$.email", is(modelMapper.map(user, UserDto.class).getEmail())));
    }

    @Test
    void getAllUsers() throws Exception {
        when(userService.getAllUsers())
                .thenReturn(List.of(user));
        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(modelMapper.map(user, UserDto.class).getId()), Long.class))
                .andExpect(jsonPath("$.[0].name", is(modelMapper.map(user, UserDto.class).getName())))
                .andExpect(jsonPath("$.[0].email", is(modelMapper.map(user, UserDto.class).getEmail())));
    }

    @Test
    void update() throws Exception {
        when(userService.update(any(), anyLong()))
                .thenReturn(user);
        mvc.perform(patch("/users/1")
                        .content(mapper.writeValueAsString(userDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(modelMapper.map(user, UserDto.class).getId()), Long.class))
                .andExpect(jsonPath("$.name", is(modelMapper.map(user, UserDto.class).getName())))
                .andExpect(jsonPath("$.email", is(modelMapper.map(user, UserDto.class).getEmail())));
    }

    @Test
    void deleteById() throws Exception {
        mvc.perform(delete("/users/1"))
                .andExpect(status().isOk());
    }
}