package com.pranil.gymmanagement.gym_man.entity;

import com.pranil.gymmanagement.gym_man.enums.MembershipStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@Entity
@Table(name = "member_memberships")
public class MemberMembership extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberMembershipId;

    private LocalDate startDate;

    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    private MembershipStatus membershipStatus;


    @ManyToOne(fetch = FetchType.LAZY) // many memberships -> one member
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;


    @ManyToOne(fetch = FetchType.LAZY) // many memberships -> one plan
    @JoinColumn(name = "plan_id", nullable = false)
    private MembershipPlan membershipPlan;
 /*
 The Receipt/Contract (MemberMembership): Because a Member might buy a 3-month plan in January,
 and a 6-month plan in April, you can't just link the Member directly to the Plan.
 Instead, you create a MemberMembership.
 This entity sits directly between them as the actual "contract."


 The Result: One Member can have many MemberMembership contracts over their lifetime.
 At the same time, one MembershipPlan (like the "Annual Pro Plan") will be linked to many
 different MemberMembership contracts across all your customers.

 */
}
