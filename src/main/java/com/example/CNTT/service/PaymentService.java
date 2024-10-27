
package com.example.CNTT.service;

import com.example.CNTT.entity.Payment;
import com.example.CNTT.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    /**
     * Saves payment information to the database.
     *
     * @param orderId      the ID of the order
     * @param payerId      the ID of the payer
     * @param payerName    the name of the payer
     * @param payerEmail   the email of the payer
     * @param payerAddress the address of the payer
     * @param amount       the amount paid
     * @param currency     the currency of the payment
     * @param status       the payment status
     * @return the saved Payment entity
     */
    public Payment savePayment(String orderId, String payerId, String payerName, String payerEmail,
                               String payerAddress, double amount, String currency, String status) {
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setPayerId(payerId);
        payment.setPayerName(payerName);
        payment.setPayerEmail(payerEmail);
        payment.setPayerAddress(payerAddress);
        payment.setAmount(amount);
        payment.setCurrency(currency);
        payment.setStatus(status);
        payment.setPaymentDate(LocalDateTime.now());

        return paymentRepository.save(payment);
    }
}