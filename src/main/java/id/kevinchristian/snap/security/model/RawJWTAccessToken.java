package id.kevinchristian.snap.security.model;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.security.Key;

public class RawJWTAccessToken implements Token {
    private String token;

    public RawJWTAccessToken(String token) {
        super();
        this.token = token;
    }

    public Jws<Claims> parseClaims(Key signingKey) {
        return Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token);
    }

    @Override
    public String getToken() {
        return token;
    }
}
