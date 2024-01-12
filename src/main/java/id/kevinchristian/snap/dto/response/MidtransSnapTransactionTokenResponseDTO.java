package id.kevinchristian.snap.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record MidtransSnapTransactionTokenResponseDTO(
    String token,
    String redirectURL
) {
}
