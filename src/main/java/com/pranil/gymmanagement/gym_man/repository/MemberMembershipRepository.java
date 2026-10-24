package com.pranil.gymmanagement.gym_man.repository;

import com.pranil.gymmanagement.gym_man.entity.MemberMembership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberMembershipRepository extends JpaRepository<MemberMembership, Long> {

    // Get all memberships for a specific member
    List<MemberMembership> findAllByMember_MemberId(Long memberId);
}