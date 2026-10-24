package com.pranil.gymmanagement.gym_man.service.interfaces;

import com.pranil.gymmanagement.gym_man.dto.LoginRequestDto;
import com.pranil.gymmanagement.gym_man.dto.UserRequestDto;
import com.pranil.gymmanagement.gym_man.dto.UserResponseDto;

import java.util.List;

public interface UserService {

    UserResponseDto registerUser(UserRequestDto requestDto);

    UserResponseDto login(LoginRequestDto loginRequestDto);

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(Long userId);

    void deactivateUser(Long userId);
}