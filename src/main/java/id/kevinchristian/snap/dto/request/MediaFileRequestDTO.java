package id.kevinchristian.snap.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MediaFileRequestDTO(
    @NotBlank
    String fileName,
    @NotBlank
    String fileType,
    @NotBlank
    String fileURL
) {
    
}
