package com.pranil.gymmanagement.gym_man.dto;

import com.pranil.gymmanagement.gym_man.enums.MembershipStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberMembershipResponseDto {

    private Long memberMembershipId;

    // Member details
    private Long memberId;
    private String memberName;
    private String memberCode;

    // Plan details
    private Long planId;
    private String planName;

    // Contract details
    private LocalDate startDate;
    private LocalDate expiryDate;
    private MembershipStatus membershipStatus;
}