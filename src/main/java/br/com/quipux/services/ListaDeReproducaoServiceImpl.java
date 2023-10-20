package br.com.quipux.services;

import br.com.quipux.dto.ListaDeReproducaoDTO;
import br.com.quipux.entities.ListaDeReproducao;
import br.com.quipux.repositories.ListaDeReproducaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListaDeReproducaoServiceImpl implements ListaDeReproducaoService {
    private final ListaDeReproducaoRepository listaDeReproducaoRepository;

    @Autowired
    public ListaDeReproducaoServiceImpl(ListaDeReproducaoRepository listaDeReproducaoRepository) {
        this.listaDeReproducaoRepository = listaDeReproducaoRepository;
    }

    @Override
    public List<ListaDeReproducao> listAllListasDeReproducao() {
        List<ListaDeReproducao> playlists = listaDeReproducaoRepository.findAll();
        return playlists;
    }

    @Override
    public ListaDeReproducao getListaDeReproducaoById(Integer id) {
        ListaDeReproducao playlist = listaDeReproducaoRepository.findById(id).orElse(null);
        return playlist;
    }

    @Override
    public ListaDeReproducao getListaDeReproducaoByNome(String nome) {
        ListaDeReproducao playlist = listaDeReproducaoRepository.findByNome(nome);
        return playlist;
    }

    @Override
    public ListaDeReproducao saveListaDeReproducao(ListaDeReproducao listaDeReproducao) {
        return listaDeReproducaoRepository.save(listaDeReproducao);
    }

    @Override
    public void deleteListaDeReproducao(Integer id) {
        listaDeReproducaoRepository.deleteById(id);
    }

    @Override
    public List<ListaDeReproducao> getListasDeReproducaoByNome(String listName) {
        return listaDeReproducaoRepository.findAllByNome(listName);
    }
}
