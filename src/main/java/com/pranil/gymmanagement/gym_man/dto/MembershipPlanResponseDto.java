package com.pranil.gymmanagement.gym_man.dto;

import com.pranil.gymmanagement.gym_man.enums.PlanStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class MembershipPlanResponseDto {
    private Long planId;

    private String planName;

    private Integer durationInDays;

    private BigDecimal price;

    private String description;

    private PlanStatus planStatus;
}
