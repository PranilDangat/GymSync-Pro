package com.pranil.gymmanagement.gym_man.controller;

import com.pranil.gymmanagement.gym_man.dto.PaymentRequestDto;
import com.pranil.gymmanagement.gym_man.dto.PaymentResponseDto;
import com.pranil.gymmanagement.gym_man.service.interfaces.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDto> recordPayment(@Valid @RequestBody PaymentRequestDto requestDto) {
        PaymentResponseDto responseDto = paymentService.recordPayment(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponseDto>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponseDto> getPaymentById(@PathVariable Long paymentId) {
        return ResponseEntity.ok(paymentService.getPaymentById(paymentId));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<PaymentResponseDto>> getPaymentsByMemberId(@PathVariable Long memberId) {
        return ResponseEntity.ok(paymentService.getPaymentsByMemberId(memberId));
    }
}