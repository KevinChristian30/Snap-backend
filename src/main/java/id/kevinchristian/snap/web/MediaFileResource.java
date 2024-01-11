package id.kevinchristian.snap.web;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import id.kevinchristian.snap.dto.response.MediaFileResponseDTO;
import id.kevinchristian.snap.service.MediaFileService;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@AllArgsConstructor
public class MediaFileResource {
    private final MediaFileService mediaFileService;

    @PostMapping("/v1/media-file")
    public ResponseEntity<MediaFileResponseDTO> postMethodName(@RequestBody MultipartFile file) {
        return ResponseEntity.ok(mediaFileService.upload(file));
    }
}
