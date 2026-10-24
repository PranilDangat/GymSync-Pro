package com.pranil.gymmanagement.gym_man.dto;

import com.pranil.gymmanagement.gym_man.enums.PlanStatus;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MembershipPlanRequestDto {
    @NotBlank(message = "Plan name is required.")
    @Size(max = 30,message = "Plan name cannot exceed 30 characters.")
    private String planName;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 day")
    private Integer durationInDays;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false,
            message = "Price must be greater than 0")
    private BigDecimal price;

    @Size(max = 255,
            message = "Description cannot exceed 255 characters")
    private String description;

}
