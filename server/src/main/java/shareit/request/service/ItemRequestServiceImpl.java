package shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import shareit.exception.RequestNotFoundException;
import shareit.exception.RequestValidException;
import shareit.exception.UserNotFoundException;
import shareit.item.dto.ItemDto;
import shareit.item.repository.ItemRepository;
import shareit.page.OffsetLimitPageable;
import shareit.request.dto.ItemRequestDto;
import shareit.request.model.ItemRequest;
import shareit.request.repository.ItemRequestRepository;
import shareit.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Andrey Boyarov
 */
@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ModelMapper modelMapper;
    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public ItemRequest addNewRequest(long userId, ItemRequestDto itemRequestDto) throws UserNotFoundException {
        ItemRequest itemRequest = modelMapper.map(itemRequestDto, ItemRequest.class);
        itemRequest.setRequestor(userRepository.findById(userId).orElseThrow(UserNotFoundException::new));
        return itemRequestRepository.save(itemRequest);
    }

    @Override
    public List<ItemRequestDto> getRequestsByUserId(long userId) throws UserNotFoundException {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return mapper(itemRequestRepository.findAllByRequestorIdOrderByCreatedAsc(userId));


    }

    @Override
    public List<ItemRequestDto> getAllRequests(long userId, int from, int size) throws RequestValidException {
        if (size < 1 || from < 0) {
            throw new RequestValidException();
        }
        return mapper(itemRequestRepository.findAll(OffsetLimitPageable.of(from, size))
                .stream()
                .collect(Collectors.toList()))
                .stream()
                .filter(itemRequestDto -> !itemRequestDto.getRequestor().getId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public ItemRequestDto getRequest(long requestId, long userId) throws UserNotFoundException, RequestNotFoundException {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        ItemRequestDto itemRequestDto = modelMapper
                .map(itemRequestRepository.findById(requestId).orElseThrow(RequestNotFoundException::new),
                        ItemRequestDto.class);
        itemRequestDto.setItems(itemRepository.findByRequestId(itemRequestDto.getId()).stream()
                .map(item -> {
                    ItemDto itemDto = modelMapper.map(item, ItemDto.class);
                    itemDto.setRequestId(item.getRequest().getId());
                    return itemDto;
                }).collect(Collectors.toList()));;
        return itemRequestDto;
    }

    private List<ItemRequestDto> mapper(List<ItemRequest> list) {
        return list
                .stream()
                .map(itemRequest -> {
                    ItemRequestDto itemRequestDto = modelMapper.map(itemRequest, ItemRequestDto.class);
                    itemRequestDto.setItems(itemRepository.findByRequestId(itemRequestDto.getId())
                            .stream()
                            .map(item -> {
                                ItemDto itemDto = modelMapper.map(item, ItemDto.class);
                                itemDto.setRequestId(item.getRequest().getId());
                                return itemDto;
                            }).collect(Collectors.toList()));
                    return itemRequestDto;
                })
                .collect(Collectors.toList());
    }
}
