package com.pranil.gymmanagement.gym_man.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberMembershipRequestDto {

    @NotNull(message = "Member ID is required")
    private Long memberId;

    @NotNull(message = "Plan ID is required")
    private Long planId;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;
}
