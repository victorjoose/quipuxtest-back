package br.com.quipux.services;

import br.com.quipux.entities.AuthToken;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public interface AuthTokenService {

    AuthToken saveToken(String token, String username, Date expirationDate);

    AuthToken getTokenByToken(String token);
}
