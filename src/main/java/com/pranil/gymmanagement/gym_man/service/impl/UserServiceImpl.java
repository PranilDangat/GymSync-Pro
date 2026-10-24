package com.pranil.gymmanagement.gym_man.service.impl;

import com.pranil.gymmanagement.gym_man.dto.LoginRequestDto;
import com.pranil.gymmanagement.gym_man.dto.UserRequestDto;
import com.pranil.gymmanagement.gym_man.dto.UserResponseDto;
import com.pranil.gymmanagement.gym_man.entity.User;
import com.pranil.gymmanagement.gym_man.enums.UserStatus;
import com.pranil.gymmanagement.gym_man.exception.DuplicateResourceException;
import com.pranil.gymmanagement.gym_man.exception.ResourceNotFoundException;
import com.pranil.gymmanagement.gym_man.repository.UserRepository;
import com.pranil.gymmanagement.gym_man.service.interfaces.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserResponseDto registerUser(UserRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new DuplicateResourceException("Email is already registered.");
        }

        User user = new User();
        user.setFullName(requestDto.getFullName());
        user.setEmail(requestDto.getEmail());

        // TODO: In production, wrap this in a BCryptPasswordEncoder
        // e.g., user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setPassword(requestDto.getPassword());

        user.setRole(requestDto.getRole());
        user.setStatus(UserStatus.ACTIVE);

        User savedUser = userRepository.save(user);
        return mapToResponseDto(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with this email."));

        if (user.getStatus() == UserStatus.INACTIVE) {
            throw new IllegalArgumentException("User account is inactive. Please contact the owner.");
        }

        // TODO: In production, use BCryptPasswordEncoder.matches()
        if (!user.getPassword().equals(loginRequestDto.getPassword())) {
            throw new IllegalArgumentException("Invalid password.");
        }

        // TODO: In a real app, you would generate and return a JWT Token here instead of just the User DTO.
        return mapToResponseDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> responseDtos = new ArrayList<>();

        for (User user : users) {
            responseDtos.add(mapToResponseDto(user));
        }

        return responseDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return mapToResponseDto(user);
    }

    @Override
    @Transactional
    public void deactivateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);
    }

    private UserResponseDto mapToResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setUserId(user.getUserId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());
        // Notice we DO NOT send the password back to the client!
        return dto;
    }
}