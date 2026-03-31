package com.todoapp.todo_service.customexception;

public class UnauthorizedTodoAccessException extends RuntimeException {
    public UnauthorizedTodoAccessException(String message) {
        super(message);
    }
}