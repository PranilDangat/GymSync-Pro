package com.pranil.gymmanagement.gym_man.entity;

import com.pranil.gymmanagement.gym_man.enums.UserRole;
import com.pranil.gymmanagement.gym_man.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(
            name = "full_name",
            nullable = false,
            length = 50
    )
    private String fullName;

    @Column(
            nullable = false,
            unique = true,
            length = 100
    )
    private String email;

    @Column(
            nullable = false
    )
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;
}
