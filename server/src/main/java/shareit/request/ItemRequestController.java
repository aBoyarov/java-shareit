package shareit.request;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import shareit.exception.RequestNotFoundException;
import shareit.exception.RequestValidException;
import shareit.exception.UserNotFoundException;
import shareit.request.dto.ItemRequestDto;
import shareit.request.service.ItemRequestService;

import java.util.List;

/**
 * @author Andrey Boyarov
 */
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {

    private final ModelMapper modelMapper;

    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestDto addNewRequest(@RequestHeader("X-Sharer-User-Id")long userId,
                                        @RequestBody ItemRequestDto itemRequestDto) throws UserNotFoundException {
        return modelMapper.map(itemRequestService.addNewRequest(userId, itemRequestDto), ItemRequestDto.class);
    }

    @GetMapping
    public List<ItemRequestDto> getRequestsByUserId(@RequestHeader("X-Sharer-User-Id")long userId) throws UserNotFoundException {
        return itemRequestService.getRequestsByUserId(userId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getAllRequests(@RequestHeader("X-Sharer-User-Id")long userId,
                                               @RequestParam(required = false, defaultValue = "0") int from,
                                               @RequestParam(required = false, defaultValue = "20") int size) throws RequestValidException{
        return itemRequestService.getAllRequests(userId, from, size);
    }

    @GetMapping("{requestId}")
    public ItemRequestDto getRequest(@RequestHeader("X-Sharer-User-Id")long userId,
                                     @PathVariable long requestId) throws UserNotFoundException, RequestNotFoundException {
        return itemRequestService.getRequest(requestId, userId);

    }
}
