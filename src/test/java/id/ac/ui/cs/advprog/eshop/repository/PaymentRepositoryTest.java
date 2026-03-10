package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    PaymentRepository paymentRepository;
    Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
    }

    @Test
    void testSavePayment() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.VOUCHER.getValue(), paymentData);
        Payment saved = paymentRepository.save(payment);
        assertEquals(payment.getId(), saved.getId());
    }

    @Test
    void testSavePaymentUpdatesExisting() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.VOUCHER.getValue(), paymentData);
        paymentRepository.save(payment);

        Payment updated = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.VOUCHER.getValue(), paymentData, PaymentStatus.SUCCESS.getValue());
        paymentRepository.save(updated);

        Payment result = paymentRepository.findById("13652556-012a-4c07-b546-54eb1396d79b");
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
    }

    @Test
    void testFindByIdFound() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.VOUCHER.getValue(), paymentData);
        paymentRepository.save(payment);

        Payment result = paymentRepository.findById("13652556-012a-4c07-b546-54eb1396d79b");
        assertNotNull(result);
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testFindByIdNotFound() {
        Payment result = paymentRepository.findById("nonexistent-id");
        assertNull(result);
    }

    @Test
    void testFindAllPayments() {
        Map<String, String> data2 = new HashMap<>();
        data2.put("bankName", "BCA");
        data2.put("referenceCode", "REF123456789012");

        Payment payment1 = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.VOUCHER.getValue(), paymentData);
        Payment payment2 = new Payment("7f9e15bb-4b15-42f4-aebc-c3af385fb078",
                PaymentMethod.BANK_TRANSFER.getValue(), data2);

        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        assertEquals(2, paymentRepository.findAll().size());
    }
}
