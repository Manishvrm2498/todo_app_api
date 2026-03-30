package com.todoapp.todo_service.Repository;


import com.todoapp.todo_service.Entity.TodoEntity;
import com.todoapp.todo_service.Entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;



@Repository
public interface TodoRepository extends JpaRepository<TodoEntity,Long> {

    String deleteById(Optional<TodoEntity> todoId);

    Page<TodoEntity> findByUserEntity(UserEntity user, Pageable pageable);

    List<TodoEntity> findByUserEntityId(Long userId);


}
