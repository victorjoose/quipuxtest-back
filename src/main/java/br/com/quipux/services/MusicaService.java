package br.com.quipux.services;

import br.com.quipux.entities.Musica;

import java.util.List;

public interface MusicaService {

    Iterable<Musica> listAllMusicas();

    Musica getMusicaById(Integer id);

    Musica getMusicaByTitulo(String titulo);

    Musica saveMusica(Musica musica);

    void deleteMusica(Integer id);

    List<Musica> getMusicaByIds(List<Integer> musicasIds);
}
