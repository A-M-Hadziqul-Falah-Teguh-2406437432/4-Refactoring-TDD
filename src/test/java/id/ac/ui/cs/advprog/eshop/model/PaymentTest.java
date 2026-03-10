package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentTest {
    Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<>();
        this.paymentData.put("voucherCode", "ESHOP1234ABC5678");
    }

    @Test
    void testCreatePaymentValid() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "VOUCHER", paymentData);
        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals(paymentData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentEmptyMethod() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("13652556-012a-4c07-b546-54eb1396d79b", "", paymentData);
        });
    }

    @Test
    void testCreatePaymentEmptyPaymentData() {
        this.paymentData.clear();
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("13652556-012a-4c07-b546-54eb1396d79b", "VOUCHER", paymentData);
        });
    }
}
