package com.pranil.gymmanagement.gym_man.entity;


import com.pranil.gymmanagement.gym_man.enums.Gender;
import com.pranil.gymmanagement.gym_man.enums.TrainerStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "trainers")
public class Trainer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trainerId;

    @Column(
            name = "trainer_code",
            nullable = false,
            unique = true,
            length = 20
    )
    private String trainerCode;

    @Column(
            name = "first_name",
            nullable = false,
            length = 25
    )
    private String firstName;

    @Column(
            name = "last_name",
            nullable = false,
            length = 25
    )
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate dateOfBirth;

    @Column(
            name = "phone_number",
            nullable = false,
            unique = true,
            length = 10
    )
    private String phoneNumber;

    @Column(
            unique = true,
            length = 100
    )
    private String email;

    @Column(
            nullable = false,
            length = 25
    )
    private String locality;

    @Column(
            nullable = false,
            length = 6
    )
    private String pinCode;

    @Column(name = "profile_photo_url")
    private String profilePhotoUrl;

    @Column(length = 100)
    private String specialization;

    @Column(name = "experience_years")
    private Integer experience;

    @Column(precision = 10, scale = 2)
    private BigDecimal salary;

    private LocalDate joiningDate;

    @Enumerated(EnumType.STRING)
    private TrainerStatus trainerStatus;

    @OneToMany(
            mappedBy = "trainer",
            fetch = FetchType.LAZY
    )
    private List<Member> members = new ArrayList<>();
    // OneToMany : one single trainer can be assigned to multiple members.
    // However, if you look at a specific Member's profile,
    // they will only have exactly one primary Trainer assigned to them at a time.




}
