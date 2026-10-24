package com.pranil.gymmanagement.gym_man.service.impl;

import com.pranil.gymmanagement.gym_man.dto.MembershipPlanRequestDto;
import com.pranil.gymmanagement.gym_man.dto.MembershipPlanResponseDto;
import com.pranil.gymmanagement.gym_man.entity.MembershipPlan;
import com.pranil.gymmanagement.gym_man.enums.PlanStatus;
import com.pranil.gymmanagement.gym_man.exception.DuplicateResourceException;
import com.pranil.gymmanagement.gym_man.exception.ResourceNotFoundException;
import com.pranil.gymmanagement.gym_man.repository.MembershipPlanRepository;
import com.pranil.gymmanagement.gym_man.service.interfaces.MembershipPlanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MembershipPlanServiceImpl implements MembershipPlanService {

    private final MembershipPlanRepository membershipPlanRepository;

    public MembershipPlanServiceImpl(MembershipPlanRepository membershipPlanRepository) {
        this.membershipPlanRepository = membershipPlanRepository;
    }

    private void validateDuplicatePlanName(MembershipPlanRequestDto requestDto) {
        if (membershipPlanRepository.existsByPlanName(requestDto.getPlanName())) {
            throw new DuplicateResourceException("Plan name already exists");
        }
    }

    @Override
    @Transactional
    public MembershipPlanResponseDto addPlan(
            MembershipPlanRequestDto requestDto
    ) {

        validateDuplicatePlanName(requestDto);


        MembershipPlan plan = new MembershipPlan();

        plan.setPlanName(requestDto.getPlanName());
        plan.setDurationInDays(requestDto.getDurationInDays());
        plan.setPrice(requestDto.getPrice());
        plan.setDescription(requestDto.getDescription());

        plan.setPlanStatus(PlanStatus.ACTIVE);


        MembershipPlan savedPlan =
                membershipPlanRepository.save(plan);


        return mapToResponseDto(savedPlan);
    }


    @Override
    @Transactional(readOnly = true)
    public List<MembershipPlanResponseDto> getAllPlans() {

        List<MembershipPlan> plans =
                membershipPlanRepository
                        .findAllByPlanStatusNot(
                                PlanStatus.DELETED
                        );


        List<MembershipPlanResponseDto> response =
                new ArrayList<>();


        for (MembershipPlan plan : plans) {

            response.add(
                    mapToResponseDto(plan)
            );
        }

        return response;
    }


    @Override
    @Transactional(readOnly = true)
    public MembershipPlanResponseDto getPlanById(
            Long planId
    ) {

        MembershipPlan plan =
                membershipPlanRepository.findById(planId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Membership plan not found with id: "
                                                + planId
                                )
                        );


        if(plan.getPlanStatus() == PlanStatus.DELETED){

            throw new ResourceNotFoundException(
                    "Membership plan not found with id: "
                            + planId
            );
        }


        return mapToResponseDto(plan);
    }


    @Override
    @Transactional
    public MembershipPlanResponseDto updatePlan(
            Long planId,
            MembershipPlanRequestDto requestDto
    ) {


        MembershipPlan plan =
                membershipPlanRepository.findById(planId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Membership plan not found with id: "
                                                + planId
                                )
                        );


        if(plan.getPlanStatus() == PlanStatus.DELETED){

            throw new ResourceNotFoundException(
                    "Membership plan not found with id: "
                            + planId
            );
        }


        // check duplicate name only if name changes
        if(!plan.getPlanName()
                .equals(requestDto.getPlanName())
                &&
                membershipPlanRepository.existsByPlanName(
                        requestDto.getPlanName()
                )){

            throw new DuplicateResourceException(
                    "Plan name already exists."
            );
        }


        plan.setPlanName(requestDto.getPlanName());
        plan.setDurationInDays(requestDto.getDurationInDays());
        plan.setPrice(requestDto.getPrice());
        plan.setDescription(requestDto.getDescription());


        MembershipPlan updatedPlan =
                membershipPlanRepository.save(plan);


        return mapToResponseDto(updatedPlan);
    }


    @Override
    @Transactional
    public void deletePlan(Long planId) {


        MembershipPlan plan =
                membershipPlanRepository.findById(planId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Membership plan not found with id: "
                                                + planId
                                )
                        );

        plan.setPlanStatus(PlanStatus.DELETED);
        membershipPlanRepository.save(plan);
    }


    private MembershipPlanResponseDto mapToResponseDto(
            MembershipPlan plan
    ) {

        MembershipPlanResponseDto response =
                new MembershipPlanResponseDto();

        response.setPlanId(plan.getPlanId());
        response.setPlanName(plan.getPlanName());
        response.setDurationInDays(plan.getDurationInDays());
        response.setPrice(plan.getPrice());
        response.setDescription(plan.getDescription());
        response.setPlanStatus(plan.getPlanStatus());

        return response;
    }

}

