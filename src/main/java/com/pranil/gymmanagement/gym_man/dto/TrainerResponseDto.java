package com.pranil.gymmanagement.gym_man.dto;

import com.pranil.gymmanagement.gym_man.enums.Gender;
import com.pranil.gymmanagement.gym_man.enums.TrainerStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
public class TrainerResponseDto {

    private Long trainerId;
    private String trainerCode;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String email;
    private String locality;
    private String pinCode;
    private String profilePhotoUrl;
    private String specialization;
    private Integer experience;
    private BigDecimal salary;
    private LocalDate joiningDate;
    private TrainerStatus trainerStatus;

}
