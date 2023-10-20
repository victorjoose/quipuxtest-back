package br.com.quipux.services;

import br.com.quipux.entities.AuthToken;
import br.com.quipux.repositories.AuthTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthTokenServiceImpl implements AuthTokenService {

    private final AuthTokenRepository authTokenRepository;

    public AuthTokenServiceImpl(AuthTokenRepository authTokenRepository) {
        this.authTokenRepository = authTokenRepository;
    }

    @Override
    public AuthToken saveToken(String token, String username, Date expirationDate) {
        AuthToken existingToken = authTokenRepository.findByUsername(username);

        if (existingToken != null) {
            authTokenRepository.delete(existingToken);
        }

        AuthToken authToken = new AuthToken();
        authToken.setToken(token);
        authToken.setUsername(username);
        authToken.setExpirationDate(expirationDate);
        return authTokenRepository.save(authToken);
    }


    @Override
    public AuthToken getTokenByToken(String token) {
        return authTokenRepository.findByToken(token);
    }
}
