package id.kevinchristian.snap.service;

import id.kevinchristian.snap.domain.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    UserDetails findByEmail(String email);
}
