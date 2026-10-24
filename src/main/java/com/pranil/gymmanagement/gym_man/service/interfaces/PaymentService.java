package com.pranil.gymmanagement.gym_man.service.interfaces;

import com.pranil.gymmanagement.gym_man.dto.PaymentRequestDto;
import com.pranil.gymmanagement.gym_man.dto.PaymentResponseDto;

import java.util.List;

public interface PaymentService {
    PaymentResponseDto recordPayment(PaymentRequestDto requestDto);
    List<PaymentResponseDto> getAllPayments();
    PaymentResponseDto getPaymentById(Long paymentId);
    List<PaymentResponseDto> getPaymentsByMemberId(Long memberId);
}