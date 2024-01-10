package id.kevinchristian.snap.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.midtrans.httpclient.error.MidtransError;

import id.kevinchristian.snap.dto.response.MidtransSnapTransactionTokenResponseDTO;
import id.kevinchristian.snap.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@AllArgsConstructor
public class PaymentResource {
    private final PaymentService paymentService;

    @GetMapping("v1/checkout")
    public ResponseEntity<MidtransSnapTransactionTokenResponseDTO> checkout() throws MidtransError {
        return ResponseEntity.ok(paymentService.checkout());
    }
}
