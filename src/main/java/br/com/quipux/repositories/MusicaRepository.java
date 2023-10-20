package br.com.quipux.repositories;

import br.com.quipux.entities.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MusicaRepository extends JpaRepository<Musica, Integer> {
    Musica findByTitulo(String titulo);
}
