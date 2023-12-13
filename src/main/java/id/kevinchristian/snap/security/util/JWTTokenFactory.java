package id.kevinchristian.snap.security.util;

import id.kevinchristian.snap.config.properties.ApplicationProperties;
import id.kevinchristian.snap.domain.User;
import id.kevinchristian.snap.security.model.JWTAccessToken;
import id.kevinchristian.snap.util.Constants;
import id.kevinchristian.snap.util.TimeUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@AllArgsConstructor
public class JWTTokenFactory {
    private final ApplicationProperties applicationProperties;
    private final Key secret;

    public JWTAccessToken createJWTAccessToken(
            User user,
            Collection<? extends GrantedAuthority> authorities) {
        Claims claims = (Claims) Jwts.claims().setSubject(user.getEmail());
        claims.put(Constants.ClaimsKey.ROLES,
                authorities.stream().map(a -> a.getAuthority()).collect(Collectors.toList()));
        claims.put(Constants.ClaimsKey.USERNAME, user.getUsername() == null ? "" : user.getUsername());
        claims.put(Constants.ClaimsKey.IS_EMAIL_VERIFIED, user.getEmailConfirmed());

        LocalDateTime now = LocalDateTime.now();
        Date currentTime = TimeUtil.localDateTimeToDate(now);
        Date expiredTime =
                TimeUtil.localDateTimeToDate(now.plusMinutes(applicationProperties.getTokenDurationInMinutes()));

        String token =
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuer(applicationProperties.getTokenIssuer())
                        .setIssuedAt(currentTime)
                        .setExpiration(expiredTime)
                        .signWith(secret, SignatureAlgorithm.HS256)
                        .compact();

        return new JWTAccessToken(token, claims);
    }
}
