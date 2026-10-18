package com.pranil.gymmanagement.gym_man.repository;

import com.pranil.gymmanagement.gym_man.entity.Member;
import com.pranil.gymmanagement.gym_man.enums.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);

    Optional<Member> findTopByOrderByMemberIdDesc();

    List<Member> findAllByMemberStatusNot(MemberStatus memberStatus);
}
