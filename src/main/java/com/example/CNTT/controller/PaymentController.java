package com.example.CNTT.controller;

import com.example.CNTT.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // Endpoint để hiển thị trang thanh toán
    @GetMapping
    public String showPaymentPage() {
        return "checkout";  // Trả về tệp checkout.html trong thư mục templates
    }

    // Endpoint để lưu thông tin thanh toán
    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<String> savePayment(@RequestBody Map<String, Object> paymentData) {
        try {
            // Lấy các trường cần thiết từ dữ liệu JSON
            String orderId = (String) paymentData.get("orderID");
            String payerId = (String) paymentData.get("payerID");
            Map<String, Object> details = (Map<String, Object>) paymentData.get("details");

            // Lấy thông tin người thanh toán
            Map<String, Object> payerInfo = (Map<String, Object>) details.get("payer");
            String payerName = (String) ((Map<String, Object>) payerInfo.get("name")).get("given_name") + " " +
                    (String) ((Map<String, Object>) payerInfo.get("name")).get("surname");
            String payerEmail = (String) payerInfo.get("email_address");
            String payerAddress = (String) ((Map<String, Object>) payerInfo.get("address")).get("country_code");

            // Lấy chi tiết thanh toán
            Map<String, Object> purchaseUnits = (Map<String, Object>) ((Map<String, Object>) details.get("purchase_units")).get(0);
            double amount = Double.parseDouble((String) ((Map<String, Object>) purchaseUnits.get("amount")).get("value"));
            String currency = (String) ((Map<String, Object>) purchaseUnits.get("amount")).get("currency_code");
            String status = (String) details.get("status");

            // Gọi service để lưu thông tin thanh toán
            paymentService.savePayment(orderId, payerId, payerName, payerEmail, payerAddress, amount, currency, status);

            return ResponseEntity.ok("Lưu thông tin thanh toán thành công.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi khi lưu thông tin thanh toán: " + e.getMessage());
        }
    }

    // Endpoint trả về trang thành công
    @GetMapping("/success")
    public String paymentSuccess() {
        return "success";  // Trả về tệp success.html trong thư mục templates
    }

    // Endpoint trả về trang lỗi
    @GetMapping("/error")
    public String paymentError() {
        return "error";  // Trả về tệp error.html trong thư mục templates
    }
}
