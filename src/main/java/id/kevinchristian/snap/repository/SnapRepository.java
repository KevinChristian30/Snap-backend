package id.kevinchristian.snap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import id.kevinchristian.snap.domain.Snap;

public interface SnapRepository extends JpaRepository<Snap, Long> {
    
}
