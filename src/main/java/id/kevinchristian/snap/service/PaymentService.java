package id.kevinchristian.snap.service;

import com.midtrans.httpclient.error.MidtransError;

import id.kevinchristian.snap.dto.response.MidtransSnapTransactionTokenResponseDTO;

public interface PaymentService {
    MidtransSnapTransactionTokenResponseDTO checkout() throws MidtransError;
}
