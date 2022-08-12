package ru.practicum.shareit.exception;

/**
 * @author Andrey Boyarov
 */
public class ItemNotFoundException extends Exception{
    public ItemNotFoundException(String message) {
        super(message);
    }
}
