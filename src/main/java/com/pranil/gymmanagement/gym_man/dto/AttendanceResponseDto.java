package com.pranil.gymmanagement.gym_man.dto;

import com.pranil.gymmanagement.gym_man.enums.AttendanceStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AttendanceResponseDto {
    private Long attendanceId;

    private LocalDate attendanceDate;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private AttendanceStatus attendanceStatus;

    // Flattened Member Data
    private Long memberId;
    private String memberName;
    private String memberCode;
}