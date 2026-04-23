package com.todoapp.todo_service.controller;


import com.todoapp.todo_service.dto.TodoDTO;
import com.todoapp.todo_service.dto.UserDTO;
import com.todoapp.todo_service.entity.TodoEntity;
import com.todoapp.todo_service.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/add")
    public ResponseEntity<TodoDTO> addTodo(@Valid @RequestBody TodoDTO todo){
        return new ResponseEntity<>(todoService.createTodo(todo),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTodo(@PathVariable Long id){
        Object data = todoService.getTodoById(id);
        return ResponseEntity.ok(data);
    }




    @GetMapping("/my")
    public ResponseEntity<UserDTO> getMyTodos() {
        return ResponseEntity.ok(todoService.getUserTodos());
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllTodos(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {

        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<TodoEntity> todoPage = todoService.getPaginatedTodos(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("data", todoPage.getContent());
        response.put("page", page);
        response.put("limit", limit);
        response.put("total", todoPage.getTotalElements());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTodo(@PathVariable Long id,@Valid @RequestBody TodoDTO todoDTO){
        String response = todoService.updateTodoById(id,todoDTO);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long id){
          String response = todoService.deleteTodoById(id);
          return ResponseEntity.ok(response);

    }
}
