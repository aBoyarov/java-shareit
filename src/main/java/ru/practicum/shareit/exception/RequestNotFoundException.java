package ru.practicum.shareit.exception;

/**
 * @author Andrey Boyarov
 */
public class RequestNotFoundException extends Exception{
    public RequestNotFoundException(String message) {
        super(message);
    }
}
