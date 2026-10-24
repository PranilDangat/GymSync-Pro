package com.pranil.gymmanagement.gym_man.service.interfaces;

import com.pranil.gymmanagement.gym_man.dto.MembershipPlanRequestDto;
import com.pranil.gymmanagement.gym_man.dto.MembershipPlanResponseDto;

import java.util.List;

public interface MembershipPlanService {

    MembershipPlanResponseDto addPlan(
            MembershipPlanRequestDto requestDto
    );

    List<MembershipPlanResponseDto> getAllPlans();

    MembershipPlanResponseDto getPlanById(
            Long planId
    );

    MembershipPlanResponseDto updatePlan(
            Long planId,
            MembershipPlanRequestDto requestDto
    );

    void deletePlan(
            Long planId
    );
}
