package br.com.quipux.controllers;

import br.com.quipux.dto.AuthenticationRequestDto;
import br.com.quipux.security.JwtTokenProvider;
import br.com.quipux.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody AuthenticationRequestDto authenticationRequest) {
        if (userService.isValidUser(authenticationRequest.getUsername(), authenticationRequest.getPassword())) {
            String token = jwtTokenProvider.createToken(authenticationRequest.getUsername());

            Map<String, String> response = new HashMap<>();
            response.put("status", "200");
            response.put("message", "Sucesso na autenticação");
            response.put("token", token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("status", "401");
            response.put("message", "Falha na autenticação");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

}
