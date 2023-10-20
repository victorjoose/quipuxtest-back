package br.com.quipux.services;

import br.com.quipux.entities.ListaDeReproducao;

import java.util.List;

public interface ListaDeReproducaoService {

    List<ListaDeReproducao> listAllListasDeReproducao();

    ListaDeReproducao getListaDeReproducaoById(Integer id);

    ListaDeReproducao getListaDeReproducaoByNome(String nome);

    ListaDeReproducao saveListaDeReproducao(ListaDeReproducao listaDeReproducao);

    void deleteListaDeReproducao(Integer id);

    List<ListaDeReproducao> getListasDeReproducaoByNome(String listName);
}
