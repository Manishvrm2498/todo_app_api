package com.todoapp.todo_service.Controller;


import com.todoapp.todo_service.DTO.LoginRequest;
import com.todoapp.todo_service.DTO.RegisterRequest;
import com.todoapp.todo_service.Entity.UserEntity;
import com.todoapp.todo_service.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;


@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        userService.register(request);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", 201);
        response.put("message", "User registered successful");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = userService.login(request);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", 200);
        response.put("message", "Login successful");
        response.put("token", token);

        return ResponseEntity.ok(response);
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody RegisterRequest request) {
        userService.update(request);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", 200);
        response.put("message", "User updated successful");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", 204);
        response.put("message", "User deleted successful");

        return ResponseEntity.ok(response);
    }
}
