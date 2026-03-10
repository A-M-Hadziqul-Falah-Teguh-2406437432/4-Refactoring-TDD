package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Order;

import java.util.List;

public interface PaymentService {
    Payment addPayment(Order order, String method, java.util.Map<String, String> paymentData);

    Payment setStatus(Payment payment, String status);

    Payment getPayment(String paymentId);

    List<Payment> getAllPayments();
}
