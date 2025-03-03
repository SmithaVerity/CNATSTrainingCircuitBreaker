package com.example.circuitbreaker.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private static final String CIRCUIT_BREAKER_NAME = "paymentService";

    @GetMapping("/processPayment")
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "fallbackPayment")
    public String processPayment(@RequestParam(required = false, defaultValue = "false") boolean fail) {
        if (fail || new Random().nextBoolean()) {
            throw new RuntimeException("Payment service is down!");
        }
        return "Payment Processed Successfully!";
    }

    public String fallbackPayment(Exception e) {
        logger.error("Payment service failed, fallback triggered: {}", e.getMessage());
        return "Payment service is unavailable. Please try again later.";
    }
}
