package com.pranil.gymmanagement.gym_man.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponseDto {
    private LocalDateTime timestamp;

    private int status;

    private String message;

    // For validation error
    private Map<String,String> errors;



}
/*
This will create responses like:

{
    "timestamp": "2026-10-03T16:30:00",
    "status": 409,
    "message": "Phone number already exists"
}
*/
