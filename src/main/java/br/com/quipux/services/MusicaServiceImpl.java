package br.com.quipux.services;

import br.com.quipux.entities.Musica;
import br.com.quipux.repositories.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicaServiceImpl implements MusicaService {

    private final MusicaRepository musicaRepository;

    @Autowired
    public MusicaServiceImpl(MusicaRepository musicaRepository) {
        this.musicaRepository = musicaRepository;
    }

    @Override
    public Iterable<Musica> listAllMusicas() {
        return musicaRepository.findAll();
    }

    @Override
    public Musica getMusicaById(Integer id) {
        return musicaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Musica> getMusicaByIds(List<Integer> musicasIds) { return musicaRepository.findAllById(musicasIds); }

    @Override
    public Musica getMusicaByTitulo(String titulo) {
        return musicaRepository.findByTitulo(titulo);
    }

    @Override
    public Musica saveMusica(Musica musica) {
        return musicaRepository.save(musica);
    }

    @Override
    public void deleteMusica(Integer id) {
        musicaRepository.deleteById(id);
    }


}
