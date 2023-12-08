package id.kevinchristian.snap.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.kevinchristian.snap.config.properties.ApplicationProperties;
import id.kevinchristian.snap.security.util.JWTTokenFactory;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Key;


@Configuration
public class AppConfig {
    @Bean
    public ApplicationProperties applicationProperties() {
        return new ApplicationProperties();
    }

    @Bean
    public Key key(ApplicationProperties applicationProperties) {
        byte[] keyBytes = Decoders.BASE64.decode(applicationProperties.getKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Bean
    public JWTTokenFactory jwtTokenFactory(ApplicationProperties applicationProperties, Key key) {
        return new JWTTokenFactory(applicationProperties, key);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
