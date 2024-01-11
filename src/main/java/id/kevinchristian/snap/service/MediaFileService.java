package id.kevinchristian.snap.service;

import org.springframework.web.multipart.MultipartFile;

import id.kevinchristian.snap.dto.response.MediaFileResponseDTO;
import io.jsonwebtoken.io.IOException;

public interface MediaFileService {
    MediaFileResponseDTO upload(MultipartFile file) throws IOException;
}
