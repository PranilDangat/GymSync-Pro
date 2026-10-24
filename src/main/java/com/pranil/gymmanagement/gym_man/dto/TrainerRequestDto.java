package com.pranil.gymmanagement.gym_man.dto;

import com.pranil.gymmanagement.gym_man.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class TrainerRequestDto {

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

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 100)
    private String email;

    @NotBlank(message = "Locality is required")
    @Size(max = 25)
    private String locality;

    @NotBlank(message = "Pin code is required")
    @Pattern(regexp = "^\\d{6}$", message = "Pin code must be exactly 6 digits")
    private String pinCode;

    private String profilePhotoUrl;

    @Size(max = 100)
    private String specialization;

    @Min(value = 0, message = "Experience cannot be negative")
    private Integer experience;

    @NotNull(message = "Salary is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Salary must be greater than 0")
    private BigDecimal salary;

}
