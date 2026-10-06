package com.pranil.gymmanagement.gym_man.entity;

import com.pranil.gymmanagement.gym_man.enums.AttendanceStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(
        name = "attendances",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"member_id", "attendance_date"}
                )
        }
)
public class Attendance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceId;


    @Column(
            name = "attendance_date",
            nullable = false
    )
    private LocalDate attendanceDate;


    @Column(name = "check_in_time")
    private LocalTime checkInTime;


    @Column(name = "check_out_time")
    private LocalTime checkOutTime;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatus attendanceStatus;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "member_id",
            nullable = false
    )
    private Member member;
    // Many attendance belongs to one member.

}