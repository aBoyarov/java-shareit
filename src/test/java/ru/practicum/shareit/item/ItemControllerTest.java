package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemOwnerDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author Andrey Boyarov
 */
@WebMvcTest(controllers = ItemController.class)
class ItemControllerTest {
    @MockBean
    private ItemService itemService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mvc;

    User owner = new User(
            1L,
            "Ivan",
            "Ivanov@yandex.ru");
    User requestor = new User(
            2L,
            "Egor",
            "Egorov@yandex.ru");
    ItemRequest firstItemRequest = new ItemRequest(
            1L,
            "Хочу воспользоваться дрелью",
            requestor,
            LocalDateTime.now());
    ItemRequest secondItemRequest = new ItemRequest(
            2L,
            "Пазязя дайте молоток",
            requestor,
            LocalDateTime.now());
    Item firstItem = new Item(
            1L,
            "Дрель",
            "Электрическая дрель",
            true,
            owner,
            firstItemRequest);
    Item secondItem = new Item(
            2L,
            "Молоток",
            "Молоток с деревянной ручкой",
            true,
            owner,
            secondItemRequest);
    CommentDto comment = new CommentDto(
            1L,
            "Роскошная дрель",
            requestor.getName(),
            LocalDateTime.now());



    @Test
    void addNewItem() throws Exception {
        when(itemService.addNewItem(any(), anyLong()))
                .thenReturn(firstItem);
        mvc.perform(post("/items")
                        .header("X-Sharer-User-Id", 1L)
                        .content(mapper.writeValueAsString(modelMapper.map(firstItem, ItemDto.class)))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(modelMapper.map(firstItem, ItemDto.class).getId()), Long.class))
                .andExpect(jsonPath("$.name", is(modelMapper.map(firstItem, ItemDto.class).getName())))
                .andExpect(jsonPath("$.description", is(modelMapper.map(firstItem, ItemDto.class).getDescription())))
                .andExpect(jsonPath("$.available", is(modelMapper.map(firstItem, ItemDto.class).getAvailable())));
    }

    @Test
    void updateItem() throws Exception {
        when(itemService.updateItem(any(), anyLong(), anyLong()))
                .thenReturn(secondItem);
        mvc.perform(patch("/items/1")
                        .header("X-Sharer-User-Id", 1L)
                        .content(mapper.writeValueAsString(modelMapper.map(secondItem, ItemDto.class)))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(modelMapper.map(secondItem, ItemDto.class).getId()), Long.class))
                .andExpect(jsonPath("$.name", is(modelMapper.map(secondItem, ItemDto.class).getName())))
                .andExpect(jsonPath("$.description", is(modelMapper.map(secondItem, ItemDto.class).getDescription())))
                .andExpect(jsonPath("$.available", is(modelMapper.map(secondItem, ItemDto.class).getAvailable())));
    }

    @Test
    void addComment() throws Exception {
        when(itemService.addComment(anyLong(), any(), anyLong()))
                .thenReturn(comment);
        mvc.perform(post("/items/1/comment")
                        .header("X-Sharer-User-Id", 1L)
                        .content(mapper.writeValueAsString(comment))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(comment.getId()), Long.class))
                .andExpect(jsonPath("$.text", is(comment.getText())))
                .andExpect(jsonPath("$.authorName", is(comment.getAuthorName())));
    }

    @Test
    void getId() throws Exception {
        when(itemService.getId(anyLong(), anyLong()))
                .thenReturn(modelMapper.map(firstItem, ItemOwnerDto.class));
        mvc.perform(get("/items/1")
                        .header("X-Sharer-User-Id", 1L)
                        .content(mapper.writeValueAsString(modelMapper.map(firstItem, ItemOwnerDto.class)))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(modelMapper.map(firstItem, ItemOwnerDto.class).getId()), Long.class))
                .andExpect(jsonPath("$.name", is(modelMapper.map(firstItem, ItemOwnerDto.class).getName())))
                .andExpect(jsonPath("$.description", is(modelMapper.map(firstItem, ItemOwnerDto.class).getDescription())))
                .andExpect(jsonPath("$.available", is(modelMapper.map(firstItem, ItemOwnerDto.class).getAvailable())));
    }

    @Test
    void getAllItemsByOwnerId() throws Exception {
        when(itemService.getAllItemsByOwnerId(anyLong(), anyInt(), anyInt()))
                .thenReturn(List.of(modelMapper.map(firstItem, ItemOwnerDto.class)));
        mvc.perform(get("/items")
                        .param("from", "0")
                        .param("size", "2")
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(modelMapper.map(firstItem, ItemOwnerDto.class).getId()), Long.class))
                .andExpect(jsonPath("$.[0].name", is(modelMapper.map(firstItem, ItemOwnerDto.class).getName())))
                .andExpect(jsonPath("$.[0].description", is(modelMapper.map(firstItem, ItemOwnerDto.class).getDescription())))
                .andExpect(jsonPath("$.[0].available", is(modelMapper.map(firstItem, ItemOwnerDto.class).getAvailable())));
    }

    @Test
    void search() throws Exception {
        when(itemService.search(anyString(),anyInt(),anyInt()))
                .thenReturn(List.of(firstItem));
        mvc.perform(get("/items/search")
                        .param("text", "Дрель")
                        .param("from", "0")
                        .param("size", "2")
                        .header("X-Sharer-User-Id", 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(modelMapper.map(firstItem, ItemDto.class).getId()), Long.class))
                .andExpect(jsonPath("$.[0].name", is(modelMapper.map(firstItem, ItemDto.class).getName())))
                .andExpect(jsonPath("$.[0].description", is(modelMapper.map(firstItem, ItemDto.class).getDescription())))
                .andExpect(jsonPath("$.[0].available", is(modelMapper.map(firstItem, ItemDto.class).getAvailable())));
    }
}