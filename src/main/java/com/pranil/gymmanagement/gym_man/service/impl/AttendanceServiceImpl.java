package com.pranil.gymmanagement.gym_man.service.impl;

import com.pranil.gymmanagement.gym_man.dto.AttendanceResponseDto;
import com.pranil.gymmanagement.gym_man.entity.Attendance;
import com.pranil.gymmanagement.gym_man.entity.Member;
import com.pranil.gymmanagement.gym_man.enums.AttendanceStatus;
import com.pranil.gymmanagement.gym_man.exception.DuplicateResourceException;
import com.pranil.gymmanagement.gym_man.exception.ResourceNotFoundException;
import com.pranil.gymmanagement.gym_man.repository.AttendanceRepository;
import com.pranil.gymmanagement.gym_man.repository.MemberRepository;
import com.pranil.gymmanagement.gym_man.service.interfaces.AttendanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final MemberRepository memberRepository;

    public AttendanceServiceImpl(AttendanceRepository attendanceRepository, MemberRepository memberRepository) {
        this.attendanceRepository = attendanceRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public AttendanceResponseDto checkIn(Long memberId) {
        // 1. Verify Member
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + memberId));

        LocalDate today = LocalDate.now();

        // 2. Check if already checked in today to prevent violating your UniqueConstraint
        Optional<Attendance> existingAttendance = attendanceRepository.findByMember_MemberIdAndAttendanceDate(memberId, today);
        if (existingAttendance.isPresent()) {
            throw new DuplicateResourceException("Member is already checked in for today.");
        }

        // 3. Create new Attendance record
        Attendance attendance = new Attendance();
        attendance.setMember(member);
        attendance.setAttendanceDate(today);
        attendance.setCheckInTime(LocalTime.now());
        attendance.setAttendanceStatus(AttendanceStatus.PRESENT);

        Attendance savedAttendance = attendanceRepository.save(attendance);

        return mapToResponseDto(savedAttendance);
    }

    @Override
    @Transactional
    public AttendanceResponseDto checkOut(Long memberId) {
        LocalDate today = LocalDate.now();

        // 1. Find today's attendance record
        Attendance attendance = attendanceRepository.findByMember_MemberIdAndAttendanceDate(memberId, today)
                .orElseThrow(() -> new ResourceNotFoundException("No check-in record found for member today to check-out."));

        // 2. Prevent double check-out
        if (attendance.getCheckOutTime() != null) {
            throw new IllegalArgumentException("Member is already checked out for today.");
        }

        // 3. Update check-out time
        attendance.setCheckOutTime(LocalTime.now());
        Attendance updatedAttendance = attendanceRepository.save(attendance);

        return mapToResponseDto(updatedAttendance);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceResponseDto> getAttendanceHistoryByMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new ResourceNotFoundException("Member not found with id: " + memberId);
        }

        List<Attendance> attendances = attendanceRepository.findAllByMember_MemberIdOrderByAttendanceDateDesc(memberId);
        List<AttendanceResponseDto> responseDtos = new ArrayList<>();

        for (Attendance attendance : attendances) {
            responseDtos.add(mapToResponseDto(attendance));
        }

        return responseDtos;
    }

    private AttendanceResponseDto mapToResponseDto(Attendance attendance) {
        AttendanceResponseDto dto = new AttendanceResponseDto();
        dto.setAttendanceId(attendance.getAttendanceId());
        dto.setAttendanceDate(attendance.getAttendanceDate());
        dto.setCheckInTime(attendance.getCheckInTime());
        dto.setCheckOutTime(attendance.getCheckOutTime());
        dto.setAttendanceStatus(attendance.getAttendanceStatus());

        if (attendance.getMember() != null) {
            dto.setMemberId(attendance.getMember().getMemberId());
            dto.setMemberName(attendance.getMember().getFirstName() + " " + attendance.getMember().getLastName());
            dto.setMemberCode(attendance.getMember().getMemberCode());
        }

        return dto;
    }
}