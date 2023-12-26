package id.kevinchristian.snap.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    UserDetails findByEmail(String email);
}
