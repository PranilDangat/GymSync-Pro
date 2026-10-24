package com.pranil.gymmanagement.gym_man.controller;

import com.pranil.gymmanagement.gym_man.dto.MemberMembershipRequestDto;
import com.pranil.gymmanagement.gym_man.dto.MemberMembershipResponseDto;
import com.pranil.gymmanagement.gym_man.service.interfaces.MemberMembershipService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memberships")
public class MemberMembershipController {

    private final MemberMembershipService memberMembershipService;

    public MemberMembershipController(MemberMembershipService memberMembershipService) {
        this.memberMembershipService = memberMembershipService;
    }

    @PostMapping
    public ResponseEntity<MemberMembershipResponseDto> assignMembership(
            @Valid @RequestBody MemberMembershipRequestDto requestDto) {

        MemberMembershipResponseDto responseDto = memberMembershipService.assignMembership(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<MemberMembershipResponseDto>> getMembershipsByMemberId(
            @PathVariable Long memberId) {

        return ResponseEntity.ok(memberMembershipService.getMembershipsByMemberId(memberId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberMembershipResponseDto> getMembershipById(
            @PathVariable Long id) {

        return ResponseEntity.ok(memberMembershipService.getMembershipById(id));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelMembership(@PathVariable Long id) {
        memberMembershipService.cancelMembership(id);
        return ResponseEntity.noContent().build();
    }
}