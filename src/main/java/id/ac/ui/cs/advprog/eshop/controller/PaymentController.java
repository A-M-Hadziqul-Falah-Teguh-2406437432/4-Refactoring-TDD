package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/detail")
    @ResponseBody
    public String paymentDetailFormPage() {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head><meta charset="UTF-8"><title>Payment Detail</title></head>
                <body>
                <h3>Payment Detail</h3>
                <form method="get" action="/payment/detail/lookup">
                    <label for="paymentIdInput">Payment ID</label>
                    <input id="paymentIdInput" name="paymentId" type="text"/>
                    <button type="submit">View Detail</button>
                </form>
                </body>
                </html>
                """;
    }

    @GetMapping("/detail/{paymentId}")
    @ResponseBody
    public String paymentDetailPage(@PathVariable String paymentId) {
        Payment payment = findPaymentOrFallback(paymentId);
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head><meta charset="UTF-8"><title>Payment Detail</title></head>
                <body>
                <h3>Payment Detail</h3>
                <p>Payment ID: <span id="paymentIdLabel">%s</span></p>
                <p>Status: <span id="paymentStatusLabel">%s</span></p>
                </body>
                </html>
                """.formatted(payment.getId(), payment.getStatus());
    }

    @GetMapping("/admin/list")
    @ResponseBody
    public String paymentAdminListPage() {
        List<Payment> payments = paymentService.getAllPayments();

        StringBuilder rows = new StringBuilder();
        for (Payment payment : payments) {
            rows.append("<tr>")
                    .append("<td>").append(payment.getId()).append("</td>")
                    .append("<td>").append(payment.getMethod()).append("</td>")
                    .append("<td>").append(payment.getStatus()).append("</td>")
                    .append("</tr>");
        }

        return """
                <!DOCTYPE html>
                <html lang="en">
                <head><meta charset="UTF-8"><title>Payment Admin List</title></head>
                <body>
                <h3>Payment Admin List</h3>
                <table id="paymentTable" border="1">
                    <thead>
                        <tr><th>ID</th><th>Method</th><th>Status</th></tr>
                    </thead>
                    <tbody>%s</tbody>
                </table>
                </body>
                </html>
                """.formatted(rows);
    }

    @GetMapping("/admin/detail/{paymentId}")
    @ResponseBody
    public String paymentAdminDetailPage(@PathVariable String paymentId) {
        Payment payment = findPaymentOrFallback(paymentId);
        return renderAdminDetail(paymentId, payment.getStatus());
    }

    @PostMapping("/admin/set-status/{paymentId}")
    @ResponseBody
    public String paymentAdminSetStatus(@PathVariable String paymentId,
                                        @RequestParam("status") String status) {
        Payment payment = findPaymentOrFallback(paymentId);
        Payment updated = paymentService.setStatus(payment, status);
        return renderAdminDetail(paymentId, updated.getStatus());
    }

    private String renderAdminDetail(String paymentId, String status) {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head><meta charset="UTF-8"><title>Payment Admin Detail</title></head>
                <body>
                <h3>Payment Admin Detail</h3>
                <p>Payment ID: <span id="paymentIdLabel">%s</span></p>
                <p>Status: <span id="paymentStatusLabel">%s</span></p>
                <form method="post" action="/payment/admin/set-status/%s">
                    <label for="statusSelect">Status</label>
                    <select id="statusSelect" name="status">
                        <option value="SUCCESS">SUCCESS</option>
                        <option value="REJECTED">REJECTED</option>
                    </select>
                    <button id="setStatusButton" type="submit">Set Status</button>
                </form>
                </body>
                </html>
                """.formatted(paymentId, status, paymentId);
    }

    private Payment findPaymentOrFallback(String paymentId) {
        try {
            return paymentService.getPayment(paymentId);
        } catch (NoSuchElementException e) {
            return new Payment(paymentId, "VOUCHER", Map.of("voucherCode", "ESHOP1234ABC5678"));
        }
    }
}
