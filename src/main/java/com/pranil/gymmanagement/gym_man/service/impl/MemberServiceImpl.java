package com.pranil.gymmanagement.gym_man.service.impl;

import com.pranil.gymmanagement.gym_man.dto.MemberRequestDto;
import com.pranil.gymmanagement.gym_man.dto.MemberResponseDto;
import com.pranil.gymmanagement.gym_man.entity.Member;
import com.pranil.gymmanagement.gym_man.entity.Trainer;
import com.pranil.gymmanagement.gym_man.enums.MemberStatus;
import com.pranil.gymmanagement.gym_man.exception.DuplicateResourceException;
import com.pranil.gymmanagement.gym_man.exception.ResourceNotFoundException;
import com.pranil.gymmanagement.gym_man.repository.MemberRepository;
import com.pranil.gymmanagement.gym_man.repository.TrainerRepository;
import com.pranil.gymmanagement.gym_man.service.interfaces.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;

    // Constructor injection.
    MemberServiceImpl(MemberRepository memberRepository, TrainerRepository trainerRepository) {
        this.memberRepository = memberRepository;
        this.trainerRepository = trainerRepository;
        // We can use @RequiredArgsConstructor for automatic constructor that handles dependency injection.
    }


    // Add member code
    // Data Validation method
    private void validateDuplicateMemberData(MemberRequestDto requestDto) {
        if (memberRepository.existsByPhoneNumber(requestDto.getPhoneNumber())) {
            throw new DuplicateResourceException("Phone number already exists.");
        }
        if (requestDto.getEmail() != null && !requestDto.getEmail().isBlank()
                && memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new DuplicateResourceException("Email already exists.");
        }
    }

    // Generate Member code
    private String generateMemberCode() {
        Optional<Member> lastMember =
                memberRepository.findTopByOrderByMemberIdDesc();

        if (lastMember.isEmpty()) {
            return "MEM0001";
        }
        Long nextId = lastMember.get().getMemberId() + 1;
        return String.format("MEM%04d", nextId);
        // Find the latest member ID, increment it by 1, and turn it into a formatted member code starting with MEM.
    }

    @Override
    @Transactional
    public MemberResponseDto addMember(MemberRequestDto requestDto) {
        // Validate duplicate data
        validateDuplicateMemberData(requestDto);
        // 1. Map RequestDto to Entity
        Member member = new Member();
        member.setFirstName(requestDto.getFirstName());
        member.setLastName(requestDto.getLastName());
        member.setGender(requestDto.getGender());
        member.setDateOfBirth(requestDto.getDateOfBirth());
        member.setPhoneNumber(requestDto.getPhoneNumber());
        member.setEmail(requestDto.getEmail());
        member.setLocality(requestDto.getLocality());
        member.setPinCode(requestDto.getPinCode());
        member.setOccupation(requestDto.getOccupation());
        member.setBloodGroup(requestDto.getBloodGroup());
        member.setMedicalCondition(requestDto.getMedicalCondition());
        member.setAllergies(requestDto.getAllergies());
        member.setEmergencyContactName(requestDto.getEmergencyContactName());
        member.setEmergencyContactNumber(requestDto.getEmergencyContactNumber());
        member.setEmergencyContactRelationship(requestDto.getEmergencyContactRelationship());

        // 2. Business Logic: Set defaults & generate values
        member.setJoiningDate(LocalDate.now());
        member.setMemberStatus(MemberStatus.ACTIVE); // You have ACTIVE in your enum.

        // Generating a code by calling generateMemberCode().
        member.setMemberCode(generateMemberCode());

        // 3. Handle Trainer Relationship if a trainerId is provided
        if (requestDto.getTrainerId() != null) {
            Trainer trainer = trainerRepository.findById(requestDto.getTrainerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Trainer not found with ID: " + requestDto.getTrainerId()));
            member.setTrainer(trainer);
        }

        // 4. Save to Database
        Member savedMember = memberRepository.save(member);

        // 5. Map saved Entity back to Response DTO
        return mapToResponseDto(savedMember);

    }

    private MemberResponseDto mapToResponseDto(Member member) {
        MemberResponseDto responseDto = new MemberResponseDto();
        responseDto.setMemberId(member.getMemberId());
        responseDto.setMemberCode(member.getMemberCode());
        responseDto.setFirstName(member.getFirstName());
        responseDto.setLastName(member.getLastName());
        responseDto.setGender(member.getGender());
        responseDto.setPhoneNumber(member.getPhoneNumber());
        responseDto.setEmail(member.getEmail());
        responseDto.setLocality(member.getLocality());
        responseDto.setProfilePhotoUrl(member.getProfilePhotoUrl());
        responseDto.setJoiningDate(member.getJoiningDate());
        responseDto.setMemberStatus(member.getMemberStatus());

        // Flatten the trainer details safely
        if (member.getTrainer() != null) {
            responseDto.setTrainerId(member.getTrainer().getTrainerId());
            responseDto.setTrainerName(member.getTrainer().getFirstName() + " " + member.getTrainer().getLastName());
        }
        return responseDto;
    }

    // Add member code finished.

    @Override
    @Transactional(readOnly = true)
    public List<MemberResponseDto> getAllMembers() {
        List<Member> members = memberRepository.findAllByMemberStatusNot(MemberStatus.DELETED);
        List<MemberResponseDto> response = new ArrayList<>();
        for (Member member : members) {
            response.add(mapToResponseDto(member));
        }
        return response;
    }

    private void validateMemberForUpdate(
            Member existingMember,
            MemberRequestDto requestDto
    ) {

        // Check phone number only if it has changed
        if (!existingMember.getPhoneNumber().equals(requestDto.getPhoneNumber())
                && memberRepository.existsByPhoneNumber(requestDto.getPhoneNumber())) {

            throw new DuplicateResourceException("Phone number already exists.");
        }

        // Check email only if it has changed
        if (requestDto.getEmail() != null
                && !requestDto.getEmail().isBlank()
                && !requestDto.getEmail().equals(existingMember.getEmail())
                && memberRepository.existsByEmail(requestDto.getEmail())) {

            throw new DuplicateResourceException("Email already exists.");
        }
    }

    @Transactional
    @Override
    public MemberResponseDto updateMember(Long memberId, MemberRequestDto requestDto) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Member not found with id: " + memberId
                        ));

        validateMemberForUpdate(member, requestDto);

        // Update fields
        member.setFirstName(requestDto.getFirstName());
        member.setLastName(requestDto.getLastName());
        member.setGender(requestDto.getGender());
        member.setDateOfBirth(requestDto.getDateOfBirth());

        member.setPhoneNumber(requestDto.getPhoneNumber());
        member.setEmail(requestDto.getEmail());

        member.setLocality(requestDto.getLocality());
        member.setPinCode(requestDto.getPinCode());

        member.setOccupation(requestDto.getOccupation());

        member.setBloodGroup(requestDto.getBloodGroup());
        member.setMedicalCondition(requestDto.getMedicalCondition());
        member.setAllergies(requestDto.getAllergies());

        member.setEmergencyContactName(requestDto.getEmergencyContactName());
        member.setEmergencyContactNumber(requestDto.getEmergencyContactNumber());
        member.setEmergencyContactRelationship(requestDto.getEmergencyContactRelationship());

        // Update trainer
        if (requestDto.getTrainerId() != null) {

            Trainer trainer = trainerRepository.findById(requestDto.getTrainerId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Trainer not found with id: " + requestDto.getTrainerId()
                            ));

            member.setTrainer(trainer);
        } else {
            member.setTrainer(null);
        }

        Member updatedMember = memberRepository.save(member);

        return mapToResponseDto(updatedMember);
    }

    @Override
    @Transactional
    public void deleteMember(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Member not found with id: " + memberId
                        )
                );
        member.setMemberStatus(MemberStatus.DELETED);
        memberRepository.save(member);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberResponseDto getMemberById(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Member not found with id: " + memberId
                        )
                );

        // If member is soft deleted, don't return it
        if (member.getMemberStatus() == MemberStatus.DELETED) {
            throw new ResourceNotFoundException(
                    "Member not found with id: " + memberId
            );
        }

        return mapToResponseDto(member);
    }

}
