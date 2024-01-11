package id.kevinchristian.snap.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import id.kevinchristian.snap.dto.response.MediaFileResponseDTO;

public interface MediaFileService {
    MediaFileResponseDTO upload(MultipartFile file) throws IOException;
}
