package com.pranil.gymmanagement.gym_man.service.interfaces;

import com.pranil.gymmanagement.gym_man.dto.AttendanceResponseDto;

import java.util.List;

public interface AttendanceService {

    AttendanceResponseDto checkIn(Long memberId);

    AttendanceResponseDto checkOut(Long memberId);

    List<AttendanceResponseDto> getAttendanceHistoryByMember(Long memberId);
}