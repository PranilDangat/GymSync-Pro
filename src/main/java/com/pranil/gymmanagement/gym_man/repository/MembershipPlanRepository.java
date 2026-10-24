package com.pranil.gymmanagement.gym_man.repository;

import com.pranil.gymmanagement.gym_man.entity.MembershipPlan;
import com.pranil.gymmanagement.gym_man.enums.PlanStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembershipPlanRepository extends JpaRepository<MembershipPlan,Long> {
    boolean existsByPlanName( String planName);

    List<MembershipPlan> findAllByPlanStatusNot(PlanStatus planStatus);
}
