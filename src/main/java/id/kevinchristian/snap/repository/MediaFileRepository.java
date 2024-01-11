package id.kevinchristian.snap.repository;

import org.springframework.data.repository.CrudRepository;

import id.kevinchristian.snap.domain.MediaFile;

public interface MediaFileRepository extends CrudRepository<MediaFile, Long> {
    
}
