package com.todoapp.todo_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodoDTO {
    private Long id;

    @NotBlank(message = "Title Required")
    private String title;

    @NotBlank(message = "Description Required")
    private String description;
    private UserDTO user;

}
