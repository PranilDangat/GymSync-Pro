package com.pranil.gymmanagement.gym_man.dto;

import com.pranil.gymmanagement.gym_man.enums.UserRole;
import com.pranil.gymmanagement.gym_man.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private Long userId;
    private String fullName;
    private String email;
    private UserRole role;
    private UserStatus status;
}