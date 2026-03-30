package com.todoapp.todo_service.DTO;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({ "id", "username", "todos" })
public class UserDTO {

    private Long id;
    private String username;
    private List<TodoDTO> todos;

}
