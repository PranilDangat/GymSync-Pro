package com.pranil.gymmanagement.gym_man.controller;

import com.pranil.gymmanagement.gym_man.dto.AttendanceResponseDto;
import com.pranil.gymmanagement.gym_man.service.interfaces.AttendanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    // Quick POST endpoint for check-in. No Request Body needed!
    @PostMapping("/check-in/{memberId}")
    public ResponseEntity<AttendanceResponseDto> checkIn(@PathVariable Long memberId) {
        AttendanceResponseDto responseDto = attendanceService.checkIn(memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // PUT endpoint for check-out.
    @PutMapping("/check-out/{memberId}")
    public ResponseEntity<AttendanceResponseDto> checkOut(@PathVariable Long memberId) {
        AttendanceResponseDto responseDto = attendanceService.checkOut(memberId);
        return ResponseEntity.ok(responseDto);
    }

    // GET endpoint to see a member's history
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendanceHistory(@PathVariable Long memberId) {
        return ResponseEntity.ok(attendanceService.getAttendanceHistoryByMember(memberId));
    }
}