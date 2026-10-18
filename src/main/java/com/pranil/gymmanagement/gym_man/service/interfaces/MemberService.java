package com.pranil.gymmanagement.gym_man.service.interfaces;

import com.pranil.gymmanagement.gym_man.dto.MemberRequestDto;
import com.pranil.gymmanagement.gym_man.dto.MemberResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MemberService {
    MemberResponseDto addMember(MemberRequestDto memberRequestDto );
    List<MemberResponseDto> getAllMembers();



    MemberResponseDto updateMember(Long memberId, MemberRequestDto requestDto);

    void deleteMember(Long memberId);


    MemberResponseDto getMemberById(Long memberId);
}
