package id.kevinchristian.snap.dto.request;

import id.kevinchristian.snap.util.Constants;
import jakarta.validation.constraints.NotNull;

public record SnapUpdateRequestDTO(
    @NotNull(message = Constants.ErrorMessage.Snap.DESCRIPTION_IS_REQUIRED)
    String description
) {
    
}
