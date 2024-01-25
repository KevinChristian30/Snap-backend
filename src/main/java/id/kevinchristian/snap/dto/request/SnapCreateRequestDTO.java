package id.kevinchristian.snap.dto.request;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import id.kevinchristian.snap.util.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SnapCreateRequestDTO(
    @NotNull(message = Constants.ErrorMessage.Snap.DESCRIPTION_IS_REQUIRED)
    @Length(max = 255)
    String description,
    @NotBlank(message = Constants.ErrorMessage.Snap.MEDIA_FILE_ID_MUST_NOT_BE_BLANK)
    String mediaFileId
) {
    
}
