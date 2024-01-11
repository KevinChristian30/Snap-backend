package id.kevinchristian.snap.dto.response;

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
