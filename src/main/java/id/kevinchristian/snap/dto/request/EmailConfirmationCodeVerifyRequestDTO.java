package id.kevinchristian.snap.dto.request;

import jakarta.validation.constraints.NotBlank;

public record EmailConfirmationCodeVerifyRequestDTO(
    @NotBlank String code
) {
    
}
