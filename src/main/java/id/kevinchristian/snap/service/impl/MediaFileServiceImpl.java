package id.kevinchristian.snap.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import id.kevinchristian.snap.domain.MediaFile;
import id.kevinchristian.snap.dto.response.MediaFileResponseDTO;
import id.kevinchristian.snap.repository.MediaFileRepository;
import id.kevinchristian.snap.service.CloudStorageService;
import id.kevinchristian.snap.service.MediaFileService;
import io.jsonwebtoken.io.IOException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MediaFileServiceImpl implements MediaFileService {
    private final MediaFileRepository mediaFileRepository;
    private final CloudStorageService cloudStorageService;

    @Override
    @Transactional
    public MediaFileResponseDTO upload(MultipartFile file) throws IOException {
        MediaFileResponseDTO mediaFileResponseDTO = cloudStorageService.upload(file);
        MediaFile mediaFile = mediaFileRepository.save(
            MediaFile.builder()
                .fileName(mediaFileResponseDTO.fileName())
                .fileType(mediaFileResponseDTO.fileType())
                .fileUrl(mediaFileResponseDTO.fileURL())
                .build()
        );

        return mediaFileResponseDTO.setId(mediaFile.getSecureId());
    }
}
