package com.pranil.gymmanagement.gym_man.dto;

import com.pranil.gymmanagement.gym_man.enums.BloodGroup;
import com.pranil.gymmanagement.gym_man.enums.Gender;
import com.pranil.gymmanagement.gym_man.enums.Relationship;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class MemberRequestDto {
    
    @NotBlank(message = "First name is required")
    @Size(max = 25, message = "First name cannot exceed 25 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 25, message = "Last name cannot exceed 25 characters")
    private String lastName;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;

    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Locality is required")
    private String locality;

    @NotBlank(message = "Pin code is required")
    @Pattern(regexp = "^\\d{6}$", message = "Pin code must be exactly 6 digits")
    private String pinCode;

    private String occupation;
    private String profilePhotoUrl;
    private Integer fingerprintUserId;

    private BloodGroup bloodGroup;
    private String medicalCondition;
    private String allergies;

    @Size(max = 50)
    private String emergencyContactName;

    @Pattern(regexp = "^\\d{10}$", message = "Emergency contact must be 10 digits")
    private String emergencyContactNumber;

    private Relationship emergencyContactRelationship;

    // Notice we only ask for the ID, not the whole Trainer object!(Relationships)
    private Long trainerId;
}
