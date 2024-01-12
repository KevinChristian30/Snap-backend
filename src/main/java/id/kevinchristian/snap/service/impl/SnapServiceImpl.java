package id.kevinchristian.snap.service.impl;

import org.springframework.stereotype.Service;

import id.kevinchristian.snap.domain.MediaFile;
import id.kevinchristian.snap.domain.Snap;
import id.kevinchristian.snap.dto.request.SnapCreateRequestDTO;
import id.kevinchristian.snap.exception.ResourceNotFoundException;
import id.kevinchristian.snap.repository.MediaFileRepository;
import id.kevinchristian.snap.repository.SnapRepository;
import id.kevinchristian.snap.service.AuthService;
import id.kevinchristian.snap.service.SnapService;
import id.kevinchristian.snap.util.Constants;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SnapServiceImpl implements SnapService {
    private final SnapRepository snapRepository;
    private final MediaFileRepository mediaFileRepository;
    private final AuthService authService;

    @Override
    public void create(SnapCreateRequestDTO dto) {
        Snap snap = new Snap();
        snap.setDescription(dto.description());
        snap.setUser(authService.findCurrentUser());
        snap.setMediaFile(findMediaFile(dto.mediaFileId()));

        snapRepository.save(snap);
    }

    MediaFile findMediaFile(String mediaFileId) {
        return mediaFileRepository.findBySecureId(mediaFileId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Constants.ErrorMessage.Service.Snap.MEDIA_FILE_NOT_FOUND));
    }
}
