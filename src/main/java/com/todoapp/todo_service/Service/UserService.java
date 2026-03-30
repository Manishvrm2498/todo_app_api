package com.todoapp.todo_service.Service;

import com.todoapp.todo_service.CustomException.DuplicateResourceException;
import com.todoapp.todo_service.CustomException.ResourceNotFoundException;
import com.todoapp.todo_service.DTO.LoginRequest;
import com.todoapp.todo_service.DTO.RegisterRequest;
import com.todoapp.todo_service.Entity.UserEntity;
import com.todoapp.todo_service.Repository.UserRepository;
import com.todoapp.todo_service.Utility.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    private UserEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }


    public UserEntity register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

       return userRepository.save(user);
    }

    public String login(LoginRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        }catch (BadCredentialsException e) {
            return "Invalid username or password";
        }
        return jwtUtil.generateToken(request.getUsername());
    }

    public UserEntity update(RegisterRequest request) {

        UserEntity user = getCurrentUser();

        if(request.getUsername() != null && !request.getUsername().isBlank()){
            user.setUsername(request.getUsername());
        }

        if(request.getEmail() != null && !request.getEmail().isBlank()){
            user.setEmail(request.getEmail());
        }

        if(request.getPassword() != null && !request.getPassword().isBlank()){
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
       return userRepository.save(user);
    }

        public void delete(Long id) {
        UserEntity currentUser = getCurrentUser();
            UserEntity user = userRepository.findById(currentUser.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            userRepository.delete(user);
        }

}
