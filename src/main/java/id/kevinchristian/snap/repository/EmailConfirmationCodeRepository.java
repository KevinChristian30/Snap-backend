package id.kevinchristian.snap.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.kevinchristian.snap.domain.EmailConfirmationCode;
import id.kevinchristian.snap.domain.User;

public interface EmailConfirmationCodeRepository extends JpaRepository<EmailConfirmationCode, Long> {
    Optional<EmailConfirmationCode> findByUser(User user);
}
