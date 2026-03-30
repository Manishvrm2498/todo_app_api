package com.todoapp.todo_service.CustomException;

public class UnauthorizedTodoAccessException extends RuntimeException {
    public UnauthorizedTodoAccessException(String message) {
        super(message);
    }
}