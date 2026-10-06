package com.pranil.gymmanagement.gym_man.entity;

import com.pranil.gymmanagement.gym_man.enums.PaymentMode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(
            precision = 10,
            scale = 2,
            nullable = false
    )
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMode paymentMode;

    @Column(
            name = "transaction_id",
            length = 100
    )
    private String transactionId;


    @ManyToOne(fetch = FetchType.LAZY )// One member can have many payments:
    @JoinColumn(
            name = "member_id",
            nullable = false
    )
    private Member member;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "member_membership_id",
            nullable = false
    )
    private MemberMembership memberMembership;
/*
Therefore, the Payment connects directly to the specific Member who handed over the money,
and it also connects directly to the specific MemberMembership (the contract) that the money is funding.
*/
}