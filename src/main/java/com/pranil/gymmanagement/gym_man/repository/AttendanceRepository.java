package com.pranil.gymmanagement.gym_man.repository;

import com.pranil.gymmanagement.gym_man.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // Used to find if a member is already checked in today
    Optional<Attendance> findByMember_MemberIdAndAttendanceDate(Long memberId, LocalDate attendanceDate);

    // Get a member's complete attendance history (newest first)
    List<Attendance> findAllByMember_MemberIdOrderByAttendanceDateDesc(Long memberId);
}