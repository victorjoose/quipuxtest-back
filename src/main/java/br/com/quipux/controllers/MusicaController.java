package br.com.quipux.controllers;

import br.com.quipux.entities.Musica;
import br.com.quipux.security.JwtTokenProvider;
import br.com.quipux.services.MusicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/musicas")
public class MusicaController {

    private final MusicaService musicaService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public MusicaController(MusicaService musicaService, JwtTokenProvider jwtTokenProvider) {
        this.musicaService = musicaService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping
    public ResponseEntity<Iterable<Musica>> listMusicas(@RequestHeader("Authorization") String token) {
        if (!isValidToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Iterable<Musica> musicas = musicaService.listAllMusicas();
        if (musicas.iterator().hasNext()) {
            return ResponseEntity.ok(musicas);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/new")
    public ResponseEntity<Object> newMusica(@RequestBody Musica musica, @RequestHeader("Authorization") String token) {
        if (!isValidToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (musica == null || musica.getTitulo() == null || musica.getTitulo().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid request. Musica data is missing.");
        }

        Musica savedMusica = musicaService.saveMusica(musica);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMusica);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMusica(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        if (!isValidToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Musica deletedMusica = musicaService.getMusicaById(id);
        if (deletedMusica == null) {
            return ResponseEntity.notFound().build();
        }

        musicaService.deleteMusica(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/{titulo}")
    public ResponseEntity<Object> searchMusica(@PathVariable String titulo, @RequestHeader("Authorization") String token) {
        if (!isValidToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Musica musica = musicaService.getMusicaByTitulo(titulo);
        if (musica == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(musica);
    }

    private boolean isValidToken(String token) {
        return token != null && jwtTokenProvider.validateToken(token);
    }
}

