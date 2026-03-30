
package com.todoapp.todo_service.Service;


import com.todoapp.todo_service.CustomException.ResourceNotFoundException;
import com.todoapp.todo_service.CustomException.UnauthorizedTodoAccessException;
import com.todoapp.todo_service.DTO.TodoDTO;
import com.todoapp.todo_service.DTO.UserDTO;
import com.todoapp.todo_service.Entity.TodoEntity;
import com.todoapp.todo_service.Entity.UserEntity;
import com.todoapp.todo_service.Repository.TodoRepository;
import com.todoapp.todo_service.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    private final UserRepository userRepository;

    public TodoService(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    private UserEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public TodoDTO createTodo(TodoDTO dto) {

        UserEntity currentUser = getCurrentUser();

        TodoEntity todo = new TodoEntity();
        todo.setTitle(dto.getTitle());
        todo.setDescription(dto.getDescription());
        todo.setUserEntity(currentUser);

        TodoEntity saved = todoRepository.save(todo);

        return new TodoDTO(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                null
        );
    }

    public Page<TodoEntity> getPaginatedTodos(Pageable pageable) {
        return todoRepository.findAll(pageable);
    }


    public UserDTO getTodoById(Long id){

        TodoEntity todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found"));

        UserEntity currentUser = getCurrentUser();

        if (!todo.getUserEntity().getId().equals(currentUser.getId())) {
            throw new UnauthorizedTodoAccessException("Unauthorized");
        }

        UserEntity user = todo.getUserEntity();

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());

        TodoDTO todoDTO = new TodoDTO(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                null
        );

        userDTO.setTodos(List.of(todoDTO));

        return userDTO;
    }



    public UserDTO getUserTodos() {

        UserEntity user = getCurrentUser();

        List<TodoEntity> todos = todoRepository.findByUserEntityId(user.getId());

        List<TodoDTO> todoDTOList = todos.stream()
                .map(t -> new TodoDTO(
                        t.getId(),
                        t.getTitle(),
                        t.getDescription(),
                        null
                ))
                .toList();

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                todoDTOList
        );
    }



    public String updateTodoById(Long id, TodoDTO dto){

        TodoEntity existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));

        UserEntity currentUser = getCurrentUser();

        if (existingTodo.getUserEntity() == null ||
                !existingTodo.getUserEntity().getId().equals(currentUser.getId())) {
            throw new UnauthorizedTodoAccessException("You are not allowed to update this todo");
        }
        if(dto.getTitle() != null && !dto.getTitle().isBlank()){
            existingTodo.setTitle(dto.getTitle());
        }
        if(dto.getDescription() != null && !dto.getDescription().isBlank()){
            existingTodo.setDescription(dto.getDescription());
        }

        todoRepository.save(existingTodo);
        return "Todo Updated Successfully";
    }



    public String deleteTodoById(Long id)  {

        TodoEntity existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));

        UserEntity currentUser = getCurrentUser();

        if (existingTodo.getUserEntity() == null ||
                !existingTodo.getUserEntity().getId().equals(currentUser.getId())) {
            throw new UnauthorizedTodoAccessException("You are not allowed to delete this todo");
        }

        todoRepository.delete(existingTodo);
        return "Todo Deleted Successfully";
    }

}