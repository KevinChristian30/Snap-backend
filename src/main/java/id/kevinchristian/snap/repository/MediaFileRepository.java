package id.kevinchristian.snap.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import id.kevinchristian.snap.domain.MediaFile;

public interface MediaFileRepository extends CrudRepository<MediaFile, Long> {
    Optional<MediaFile> findBySecureId(String secureId);
}
