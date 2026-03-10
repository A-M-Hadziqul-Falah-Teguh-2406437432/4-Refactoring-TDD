package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    private static final String VOUCHER_CODE_KEY = "voucherCode";
    private static final String BANK_NAME_KEY = "bankName";
    private static final String REFERENCE_CODE_KEY = "referenceCode";
    private static final String VOUCHER_METHOD = "VOUCHER";
    private static final String BANK_TRANSFER_METHOD = "BANK_TRANSFER";

    private static final int VOUCHER_LENGTH = 16;
    private static final int VOUCHER_NUMERIC_COUNT = 8;
    private static final String VOUCHER_PREFIX = "ESHOP";

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        Payment payment = new Payment(UUID.randomUUID().toString(), method, paymentData);
        payment.setOrder(order);
        payment.setStatus(resolveInitialStatus(method, paymentData));
        return paymentRepository.save(payment);
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        payment.setStatus(status);
        updateOrderStatus(payment, status);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(String paymentId) {
        Payment payment = paymentRepository.findById(paymentId);
        if (payment == null) {
            throw new NoSuchElementException();
        }
        return payment;
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    private String resolveInitialStatus(String method, Map<String, String> paymentData) {
        if (paymentData == null) {
            return PaymentStatus.REJECTED.getValue();
        }

        return switch (method) {
            case VOUCHER_METHOD -> resolveVoucherStatus(paymentData);
            case BANK_TRANSFER_METHOD -> resolveBankTransferStatus(paymentData);
            default -> PaymentStatus.REJECTED.getValue();
        };
    }

    private String resolveVoucherStatus(Map<String, String> paymentData) {
        return isValidVoucherCode(paymentData.get(VOUCHER_CODE_KEY))
                ? PaymentStatus.SUCCESS.getValue()
                : PaymentStatus.REJECTED.getValue();
    }

    private String resolveBankTransferStatus(Map<String, String> paymentData) {
        String bankName = paymentData.get(BANK_NAME_KEY);
        String referenceCode = paymentData.get(REFERENCE_CODE_KEY);
        return isBlank(bankName) || isBlank(referenceCode)
                ? PaymentStatus.REJECTED.getValue()
                : PaymentStatus.SUCCESS.getValue();
    }

    private boolean isValidVoucherCode(String voucherCode) {
        if (voucherCode == null || voucherCode.length() != VOUCHER_LENGTH || !voucherCode.startsWith(VOUCHER_PREFIX)) {
            return false;
        }
        return countNumericCharacters(voucherCode) == VOUCHER_NUMERIC_COUNT;
    }

    private long countNumericCharacters(String value) {
        return value.chars().filter(Character::isDigit).count();
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void updateOrderStatus(Payment payment, String paymentStatus) {
        if (payment.getOrder() == null) {
            return;
        }

        if (PaymentStatus.SUCCESS.getValue().equals(paymentStatus)) {
            payment.getOrder().setStatus(OrderStatus.SUCCESS.getValue());
        } else if (PaymentStatus.REJECTED.getValue().equals(paymentStatus)) {
            payment.getOrder().setStatus(OrderStatus.FAILED.getValue());
        }
    }
}
