package com.pranil.gymmanagement.gym_man.controller;

import com.pranil.gymmanagement.gym_man.dto.MembershipPlanRequestDto;
import com.pranil.gymmanagement.gym_man.dto.MembershipPlanResponseDto;
import com.pranil.gymmanagement.gym_man.service.interfaces.MembershipPlanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/membership-plans")
public class MembershipPlanController {

    private final MembershipPlanService membershipPlanService;
    public MembershipPlanController(MembershipPlanService membershipPlanService){
        this.membershipPlanService=membershipPlanService;
    }
    @PostMapping
    public ResponseEntity<MembershipPlanResponseDto> addPlan(
            @Valid @RequestBody MembershipPlanRequestDto requestDto
    ){

        MembershipPlanResponseDto response =
                membershipPlanService.addPlan(requestDto);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }



    @GetMapping
    public ResponseEntity<List<MembershipPlanResponseDto>> getAllPlans(){

        return ResponseEntity.ok(
                membershipPlanService.getAllPlans()
        );
    }



    @GetMapping("/{planId}")
    public ResponseEntity<MembershipPlanResponseDto> getPlanById(
            @PathVariable Long planId
    ){

        return ResponseEntity.ok(
                membershipPlanService.getPlanById(planId)
        );
    }



    @PutMapping("/{planId}")
    public ResponseEntity<MembershipPlanResponseDto> updatePlan(
            @PathVariable Long planId,
            @Valid @RequestBody MembershipPlanRequestDto requestDto
    ){

        return ResponseEntity.ok(
                membershipPlanService.updatePlan(planId, requestDto)
        );
    }



    @DeleteMapping("/{planId}")
    public ResponseEntity<Void> deletePlan(
            @PathVariable Long planId
    ){

        membershipPlanService.deletePlan(planId);

        return ResponseEntity.noContent().build();
    }
}
