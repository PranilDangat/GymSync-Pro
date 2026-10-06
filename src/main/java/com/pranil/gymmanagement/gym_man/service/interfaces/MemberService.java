package com.pranil.gymmanagement.gym_man.service.interfaces;

import com.pranil.gymmanagement.gym_man.dto.MemberRequestDto;
import com.pranil.gymmanagement.gym_man.dto.MemberResponseDto;

public interface MemberService {
    MemberResponseDto addMember(MemberRequestDto memberRequestDto );
    MemberResponseDto getMemberById(Long memberId);

}
