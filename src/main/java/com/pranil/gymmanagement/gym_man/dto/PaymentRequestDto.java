package com.pranil.gymmanagement.gym_man.dto;

import com.pranil.gymmanagement.gym_man.enums.PaymentMode;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PaymentRequestDto {

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotNull(message = "Payment date is required")
    private LocalDate paymentDate;

    @NotNull(message = "Payment mode is required")
    private PaymentMode paymentMode;

    @Size(max = 100, message = "Transaction ID cannot exceed 100 characters")
    private String transactionId; // Optional: Usually blank for CASH, required for UPI/ONLINE

    @NotNull(message = "Member ID is required")
    private Long memberId;

    @NotNull(message = "Membership Contract ID is required")
    private Long memberMembershipId;
}