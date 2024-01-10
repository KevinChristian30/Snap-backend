package id.kevinchristian.snap.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.midtrans.Midtrans;
import com.midtrans.httpclient.SnapApi;
import com.midtrans.httpclient.error.MidtransError;

import id.kevinchristian.snap.dto.response.MidtransSnapTransactionTokenResponseDTO;
import id.kevinchristian.snap.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public MidtransSnapTransactionTokenResponseDTO checkout() throws MidtransError {
        Midtrans.clientKey = "SB-Mid-client-mPbXYyMCOKXv_zBe";
        Midtrans.serverKey = "SB-Mid-server-31kAlsoWTrni935TRIyiz2gM";
        Midtrans.isProduction = false;

        UUID idRand = UUID.randomUUID();
        Map<String, Object> params = new HashMap<>();

        Map<String, String> transactionDetails = new HashMap<>();
        transactionDetails.put("order_id", idRand.toString());
        transactionDetails.put("gross_amount", "100000");

        Map<String, String> creditCard = new HashMap<>();
        creditCard.put("secure", "true");

        params.put("transaction_details", transactionDetails);
        params.put("credit_card", creditCard);

        String redirectUrl = SnapApi.createTransactionRedirectUrl(params);
        String[] tokens = redirectUrl.split("/");

        return new MidtransSnapTransactionTokenResponseDTO(tokens[tokens.length - 1], redirectUrl);
    }

}
