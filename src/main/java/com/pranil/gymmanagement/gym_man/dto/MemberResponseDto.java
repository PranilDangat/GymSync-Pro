package com.pranil.gymmanagement.gym_man.dto;

import com.pranil.gymmanagement.gym_man.enums.BloodGroup;
import com.pranil.gymmanagement.gym_man.enums.Gender;
import com.pranil.gymmanagement.gym_man.enums.MemberStatus;
import com.pranil.gymmanagement.gym_man.enums.Relationship;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberResponseDto {
    private Long memberId;

    private String memberCode;

    private String firstName;

    private String lastName;

    private Gender gender;

    private LocalDate dateOfBirth;

    private String phoneNumber;

    private String email;

    private String locality;

    private String pinCode;

    private String occupation;

    private String profilePhotoUrl;

    private LocalDate joiningDate;

    private Integer fingerprintUserId;

    private BloodGroup bloodGroup;

    private String medicalCondition;

    private String allergies;

    private String emergencyContactName;

    private String emergencyContactNumber;

    private Relationship emergencyContactRelationship;

    private MemberStatus memberStatus;

    // Trainer information
    private Long trainerId;

    private String trainerName;
}
