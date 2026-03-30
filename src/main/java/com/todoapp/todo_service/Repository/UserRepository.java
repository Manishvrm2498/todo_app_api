package com.todoapp.todo_service.Repository;

import com.todoapp.todo_service.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);

    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByPassword( String password);

    UserEntity findById(long userId);

    UserEntity save(UserEntity userEntity);
}
