package id.kevinchristian.snap.security.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JWTAuthToken extends AbstractAuthenticationToken {
    private RawJWTAccessToken rawJWTAccessToken;
    private UserDetails userDetails;

    public JWTAuthToken(RawJWTAccessToken rawJWTAccessToken) {
        super(null);
        this.rawJWTAccessToken = rawJWTAccessToken;
        super.setAuthenticated(false);
    }

    public JWTAuthToken(UserDetails userDetails, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.eraseCredentials();
        this.userDetails = userDetails;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return rawJWTAccessToken;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
