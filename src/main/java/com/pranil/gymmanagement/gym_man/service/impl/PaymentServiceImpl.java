package com.pranil.gymmanagement.gym_man.service.impl;

import com.pranil.gymmanagement.gym_man.dto.PaymentRequestDto;
import com.pranil.gymmanagement.gym_man.dto.PaymentResponseDto;
import com.pranil.gymmanagement.gym_man.entity.Member;
import com.pranil.gymmanagement.gym_man.entity.MemberMembership;
import com.pranil.gymmanagement.gym_man.entity.Payment;
import com.pranil.gymmanagement.gym_man.exception.ResourceNotFoundException;
import com.pranil.gymmanagement.gym_man.repository.MemberMembershipRepository;
import com.pranil.gymmanagement.gym_man.repository.MemberRepository;
import com.pranil.gymmanagement.gym_man.repository.PaymentRepository;
import com.pranil.gymmanagement.gym_man.service.interfaces.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;
    private final MemberMembershipRepository memberMembershipRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              MemberRepository memberRepository,
                              MemberMembershipRepository memberMembershipRepository) {
        this.paymentRepository = paymentRepository;
        this.memberRepository = memberRepository;
        this.memberMembershipRepository = memberMembershipRepository;
    }

    @Override
    @Transactional
    public PaymentResponseDto recordPayment(PaymentRequestDto requestDto) {
        // 1. Fetch the Member
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + requestDto.getMemberId()));

        // 2. Fetch the Contract
        MemberMembership membership = memberMembershipRepository.findById(requestDto.getMemberMembershipId())
                .orElseThrow(() -> new ResourceNotFoundException("Membership contract not found with id: " + requestDto.getMemberMembershipId()));

        // 3. Security Validation: Ensure this contract actually belongs to this member!
        if (!membership.getMember().getMemberId().equals(member.getMemberId())) {
            throw new IllegalArgumentException("Invalid payment: This membership contract does not belong to the specified member.");
        }

        // 4. Map to Entity
        Payment payment = new Payment();
        payment.setAmount(requestDto.getAmount());
        payment.setPaymentDate(requestDto.getPaymentDate());
        payment.setPaymentMode(requestDto.getPaymentMode());
        payment.setTransactionId(requestDto.getTransactionId());
        payment.setMember(member);
        payment.setMemberMembership(membership);

        // 5. Save and Return
        Payment savedPayment = paymentRepository.save(payment);
        return mapToResponseDto(savedPayment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponseDto> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        List<PaymentResponseDto> responseDtos = new ArrayList<>();
        for (Payment payment : payments) {
            responseDtos.add(mapToResponseDto(payment));
        }
        return responseDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponseDto getPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + paymentId));
        return mapToResponseDto(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponseDto> getPaymentsByMemberId(Long memberId) {
        // Verify member exists first
        if (!memberRepository.existsById(memberId)) {
            throw new ResourceNotFoundException("Member not found with id: " + memberId);
        }

        List<Payment> payments = paymentRepository.findAllByMember_MemberId(memberId);
        List<PaymentResponseDto> responseDtos = new ArrayList<>();
        for (Payment payment : payments) {
            responseDtos.add(mapToResponseDto(payment));
        }
        return responseDtos;
    }

    private PaymentResponseDto mapToResponseDto(Payment payment) {
        PaymentResponseDto dto = new PaymentResponseDto();
        dto.setPaymentId(payment.getPaymentId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setPaymentMode(payment.getPaymentMode());
        dto.setTransactionId(payment.getTransactionId());

        if (payment.getMember() != null) {
            dto.setMemberId(payment.getMember().getMemberId());
            dto.setMemberName(payment.getMember().getFirstName() + " " + payment.getMember().getLastName());
            dto.setMemberCode(payment.getMember().getMemberCode());
        }

        if (payment.getMemberMembership() != null) {
            dto.setMemberMembershipId(payment.getMemberMembership().getMemberMembershipId());
            if (payment.getMemberMembership().getMembershipPlan() != null) {
                dto.setPlanName(payment.getMemberMembership().getMembershipPlan().getPlanName());
            }
        }

        return dto;
    }
}