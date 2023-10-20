package br.com.quipux.security;

import br.com.quipux.entities.AuthToken;
import br.com.quipux.services.AuthTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Base64;

@Service
public class JwtTokenProvider {

    private final AuthTokenService authTokenService;

    @Autowired
    public JwtTokenProvider(AuthTokenService authTokenService) {
        this.authTokenService = authTokenService;
    }

    private final String secretKey = Base64.getEncoder().encodeToString("Bearer ".getBytes());
    private final long validityInMilliseconds = 3600000;

    public String createToken(String username) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        authTokenService.saveToken("Bearer " + token, username, now);

        return token;
    }

    public boolean validateToken(String token) {
        AuthToken storedToken = authTokenService.getTokenByToken(token);
        return storedToken != null && storedToken.getToken().equals(token);
    }

}
