package com.pranil.gymmanagement.gym_man.service.interfaces;

import com.pranil.gymmanagement.gym_man.dto.MemberMembershipRequestDto;
import com.pranil.gymmanagement.gym_man.dto.MemberMembershipResponseDto;

import java.util.List;

public interface MemberMembershipService {

    MemberMembershipResponseDto assignMembership(MemberMembershipRequestDto requestDto);

    List<MemberMembershipResponseDto> getMembershipsByMemberId(Long memberId);

    MemberMembershipResponseDto getMembershipById(Long memberMembershipId);

    void cancelMembership(Long memberMembershipId);
}