package com.pranil.gymmanagement.gym_man.entity;

import com.pranil.gymmanagement.gym_man.enums.BloodGroup;
import com.pranil.gymmanagement.gym_man.enums.Gender;
import com.pranil.gymmanagement.gym_man.enums.MemberStatus;
import com.pranil.gymmanagement.gym_man.enums.Relationship;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "members")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(
            name = "member_code",
            nullable = false,
            unique = true,
            length = 20
    )
    private String memberCode;

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
            name = "email",
            unique = true
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

    @Column(
            length = 25
    )
    private String occupation;

    private String profilePhotoUrl;

    private LocalDate joiningDate;

    @Column(
            name = "fingerprint_user_id",
            unique = true
    )
    private Integer fingerprintUserId;

    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;

    private String medicalCondition;

    private String allergies;

    private String emergencyContactName;

    private String emergencyContactNumber;

    @Enumerated(EnumType.STRING)
    private Relationship emergencyContactRelationship;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;




    @ManyToOne(fetch = FetchType.LAZY) // Load trainer data only when we actually access it.
    @JoinColumn(name="trainer_id") // Member is the owning side. The table containing the FK owns the relationship.
    private Trainer trainer;
    //  Many members -> One trainer.


    @OneToMany(mappedBy = "member",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<MemberMembership> memberships = new ArrayList<>();
    /*
    One member can buy multiple Memberships.
    E.g.  The Customer (Member): The person buying the plan.


    */

    @OneToMany(
            mappedBy = "member",
            fetch = FetchType.LAZY
    )
    private List<Payment> payments = new ArrayList<>();
    /*
    One Member can pay multiple times.
    */


    @OneToMany(
            mappedBy = "member",
            fetch = FetchType.LAZY
    )
    private List<Attendance> attendances = new ArrayList<>();
// one member multiple attendance.
}
