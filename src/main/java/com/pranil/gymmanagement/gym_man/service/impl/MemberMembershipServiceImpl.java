package com.pranil.gymmanagement.gym_man.service.impl;

import com.pranil.gymmanagement.gym_man.dto.MemberMembershipRequestDto;
import com.pranil.gymmanagement.gym_man.dto.MemberMembershipResponseDto;
import com.pranil.gymmanagement.gym_man.entity.Member;
import com.pranil.gymmanagement.gym_man.entity.MemberMembership;
import com.pranil.gymmanagement.gym_man.entity.MembershipPlan;
import com.pranil.gymmanagement.gym_man.enums.MembershipStatus;
import com.pranil.gymmanagement.gym_man.exception.ResourceNotFoundException;
import com.pranil.gymmanagement.gym_man.repository.MemberMembershipRepository;
import com.pranil.gymmanagement.gym_man.repository.MemberRepository;
import com.pranil.gymmanagement.gym_man.repository.MembershipPlanRepository;
import com.pranil.gymmanagement.gym_man.service.interfaces.MemberMembershipService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberMembershipServiceImpl implements MemberMembershipService {

    private final MemberMembershipRepository memberMembershipRepository;
    private final MemberRepository memberRepository;
    private final MembershipPlanRepository membershipPlanRepository;

    public MemberMembershipServiceImpl(MemberMembershipRepository memberMembershipRepository,
                                       MemberRepository memberRepository,
                                       MembershipPlanRepository membershipPlanRepository) {
        this.memberMembershipRepository = memberMembershipRepository;
        this.memberRepository = memberRepository;
        this.membershipPlanRepository = membershipPlanRepository;
    }

    @Override
    @Transactional
    public MemberMembershipResponseDto assignMembership(MemberMembershipRequestDto requestDto) {
        // 1. Fetch Member
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + requestDto.getMemberId()));

        // 2. Fetch Plan
        MembershipPlan plan = membershipPlanRepository.findById(requestDto.getPlanId())
                .orElseThrow(() -> new ResourceNotFoundException("Membership Plan not found with id: " + requestDto.getPlanId()));

        // 3. Create Entity and calculate dates
        MemberMembership membership = new MemberMembership();
        membership.setMember(member);
        membership.setMembershipPlan(plan);
        membership.setStartDate(requestDto.getStartDate());

        // Dynamic expiry date calculation based on the plan's duration
        LocalDate expiryDate = requestDto.getStartDate().plusDays(plan.getDurationInDays());
        membership.setExpiryDate(expiryDate);

        membership.setMembershipStatus(MembershipStatus.ACTIVE);

        // 4. Save and return
        MemberMembership savedMembership = memberMembershipRepository.save(membership);
        return mapToResponseDto(savedMembership);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemberMembershipResponseDto> getMembershipsByMemberId(Long memberId) {
        // Verify member exists first
        if (!memberRepository.existsById(memberId)) {
            throw new ResourceNotFoundException("Member not found with id: " + memberId);
        }

        List<MemberMembership> memberships = memberMembershipRepository.findAllByMember_MemberId(memberId);
        List<MemberMembershipResponseDto> responseDtos = new ArrayList<>();

        for (MemberMembership membership : memberships) {
            responseDtos.add(mapToResponseDto(membership));
        }

        return responseDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public MemberMembershipResponseDto getMembershipById(Long memberMembershipId) {
        MemberMembership membership = memberMembershipRepository.findById(memberMembershipId)
                .orElseThrow(() -> new ResourceNotFoundException("Membership contract not found with id: " + memberMembershipId));
        return mapToResponseDto(membership);
    }

    @Override
    @Transactional
    public void cancelMembership(Long memberMembershipId) {
        MemberMembership membership = memberMembershipRepository.findById(memberMembershipId)
                .orElseThrow(() -> new ResourceNotFoundException("Membership contract not found with id: " + memberMembershipId));

        membership.setMembershipStatus(MembershipStatus.CANCELLED);
        memberMembershipRepository.save(membership);
    }

    private MemberMembershipResponseDto mapToResponseDto(MemberMembership membership) {
        MemberMembershipResponseDto dto = new MemberMembershipResponseDto();

        dto.setMemberMembershipId(membership.getMemberMembershipId());
        dto.setStartDate(membership.getStartDate());
        dto.setExpiryDate(membership.getExpiryDate());
        dto.setMembershipStatus(membership.getMembershipStatus());

        // Flatten member relationships safely
        if (membership.getMember() != null) {
            dto.setMemberId(membership.getMember().getMemberId());
            dto.setMemberName(membership.getMember().getFirstName() + " " + membership.getMember().getLastName());
            dto.setMemberCode(membership.getMember().getMemberCode());
        }

        // Flatten plan relationships safely
        if (membership.getMembershipPlan() != null) {
            dto.setPlanId(membership.getMembershipPlan().getPlanId());
            dto.setPlanName(membership.getMembershipPlan().getPlanName());
        }

        return dto;
    }
}