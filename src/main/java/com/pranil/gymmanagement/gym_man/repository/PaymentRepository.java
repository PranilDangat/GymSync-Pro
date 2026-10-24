package com.pranil.gymmanagement.gym_man.repository;

import com.pranil.gymmanagement.gym_man.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Get all payment history for a specific member
    List<Payment> findAllByMember_MemberId(Long memberId);

    // Get all payments made against a specific contract (useful for installment plans)
    List<Payment> findAllByMemberMembership_MemberMembershipId(Long memberMembershipId);
}