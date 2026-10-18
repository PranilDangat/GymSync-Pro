package com.pranil.gymmanagement.gym_man.controller;

import com.pranil.gymmanagement.gym_man.dto.MemberRequestDto;
import com.pranil.gymmanagement.gym_man.dto.MemberResponseDto;
import com.pranil.gymmanagement.gym_man.entity.Member;
import com.pranil.gymmanagement.gym_man.service.interfaces.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<MemberResponseDto> addMember(@Valid @RequestBody MemberRequestDto memberRequestDto) {
        MemberResponseDto memberResponseDto = memberService.addMember(memberRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(memberResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> getMemberById(@PathVariable Long memberId) {
        return ResponseEntity.ok(memberService.getMemberById(memberId));
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> updateMember(@PathVariable Long memberId,
                                                          @Valid @RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(memberService.updateMember(memberId, memberRequestDto));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberId){
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent().build();
    }
}
