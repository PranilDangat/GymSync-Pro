package com.pranil.gymmanagement.gym_man.dto;

import com.pranil.gymmanagement.gym_man.enums.PaymentMode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PaymentResponseDto {
    private Long paymentId;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private PaymentMode paymentMode;
    private String transactionId;

    // Flattened Member details
    private Long memberId;
    private String memberName;
    private String memberCode;

    // Flattened Contract details
    private Long memberMembershipId;
    private String planName;
}