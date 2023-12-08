package id.kevinchristian.snap.security.util;

import id.kevinchristian.snap.config.properties.ApplicationProperties;
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

    public JWTAccessToken createJWTAccessToken(String username, Collection<? extends GrantedAuthority> authorities) {
        Claims claims = (Claims) Jwts.claims().setSubject(username);
        claims.put(Constants.ClaimsKey.ROLES, authorities.stream().map(a -> a.getAuthority()).collect(Collectors.toList()));

        LocalDateTime now = LocalDateTime.now();
        Date currentTime = TimeUtil.localDateTimeToDate(now);
        Date expiredTime =
                TimeUtil.localDateTimeToDate(now.plusMinutes(applicationProperties.getTokenDurationInMinutes()));

        String token =
                Jwts.builder().setClaims(claims).setIssuer(applicationProperties.getTokenIssuer()).setIssuedAt(currentTime).setExpiration(expiredTime).signWith(secret, SignatureAlgorithm.HS256).compact();

        return new JWTAccessToken(token, claims);
    }
}
