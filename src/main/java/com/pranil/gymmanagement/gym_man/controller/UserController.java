package com.pranil.gymmanagement.gym_man.controller;

import com.pranil.gymmanagement.gym_man.dto.LoginRequestDto;
import com.pranil.gymmanagement.gym_man.dto.UserRequestDto;
import com.pranil.gymmanagement.gym_man.dto.UserResponseDto;
import com.pranil.gymmanagement.gym_man.service.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRequestDto requestDto) {
        UserResponseDto responseDto = userService.registerUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        UserResponseDto responseDto = userService.login(loginRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PutMapping("/{userId}/deactivate")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long userId) {
        userService.deactivateUser(userId);
        return ResponseEntity.noContent().build();
    }
}