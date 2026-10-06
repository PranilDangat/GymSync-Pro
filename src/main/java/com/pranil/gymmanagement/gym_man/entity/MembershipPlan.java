package com.pranil.gymmanagement.gym_man.entity;

import com.pranil.gymmanagement.gym_man.enums.PlanStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "membership_plans")
public class MembershipPlan extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;

    @Column(
            name = "plan_name",
            nullable = false,
            unique = true,
            length = 30
    )
    private String planName;

    @Column(name = "duration_in_days",
            nullable = false)
    private Integer durationInDays;

    @Column(
            precision = 10,
            scale = 2,
            nullable = false
    )
    private BigDecimal price;

    @Column(length = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    private PlanStatus planStatus;

    @OneToMany(
            mappedBy = "membershipPlan",
            fetch = FetchType.LAZY
    )
    private List<MemberMembership> memberships = new ArrayList<>();
    /* One Membership plan can take multiple members in MembershipMembership.
    E.g: The Menu (MembershipPlan): This is just your catalog of offerings (e.g., "Annual Pro Plan", "Monthly Basic Plan").
     */

}
