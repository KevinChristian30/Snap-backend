package id.kevinchristian.snap.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record MediaFileResponseDTO(
    String id,
    String fileName,
    String fileType,
    String fileURL
) {
    public MediaFileResponseDTO setId(String id) {
        return new MediaFileResponseDTO(id, fileName, fileType, fileURL);
    }
}
