package br.com.quipux;

import br.com.quipux.controllers.ListaDeReproducaoController;
import br.com.quipux.dto.ListaDeReproducaoDTO;
import br.com.quipux.entities.ListaDeReproducao;
import br.com.quipux.entities.Musica;
import br.com.quipux.security.JwtTokenProvider;
import br.com.quipux.services.ListaDeReproducaoService;
import br.com.quipux.services.MusicaService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ListaDeReproducaoControllerTest {

    @Autowired
    private ListaDeReproducaoController listaDeReproducaoController;

    @Autowired
    private ListaDeReproducaoService listaDeReproducaoService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MusicaService musicaService;

    @Before
    public void setup() {
        listaDeReproducaoService = mock(ListaDeReproducaoService.class);
        jwtTokenProvider = mock(JwtTokenProvider.class);
        musicaService = mock(MusicaService.class);

        listaDeReproducaoController = new ListaDeReproducaoController(
                listaDeReproducaoService, jwtTokenProvider, musicaService
        );
    }

    @Test
    public void testAdicionarNovaListaDeReproducao() {

        ListaDeReproducaoDTO playlistRequest = new ListaDeReproducaoDTO();
        playlistRequest.setNome("Exemplo Playlist");
        playlistRequest.setDescricao("Descrição");
        List<Integer> musicasIds = Arrays.asList(1);
        playlistRequest.setMusicasIds(musicasIds);

        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);

        List<Musica> mockMusicaList = Collections.singletonList(new Musica());
        when(musicaService.getMusicaByIds(musicasIds)).thenReturn(mockMusicaList);

        ListaDeReproducao mockListaDeReproducao = new ListaDeReproducao();
        when(listaDeReproducaoService.saveListaDeReproducao(any(ListaDeReproducao.class))).thenReturn(mockListaDeReproducao);

        ResponseEntity<ListaDeReproducao> response = listaDeReproducaoController.adicionarNovaListaDeReproducao(playlistRequest, anyString());

        verify(jwtTokenProvider).validateToken(anyString());
        verify(musicaService).getMusicaByIds(musicasIds);
        verify(listaDeReproducaoService).saveListaDeReproducao(any(ListaDeReproducao.class));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockListaDeReproducao, response.getBody());
    }
    @Test
    public void testListAllListasDeReproducao() {
        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);

        List<ListaDeReproducao> mockListasDeReproducao = new ArrayList<>();

        ListaDeReproducao lista1 = new ListaDeReproducao();
        lista1.setMusicas(new ArrayList<>());
        mockListasDeReproducao.add(lista1);

        ListaDeReproducao lista2 = new ListaDeReproducao();
        lista2.setMusicas(new ArrayList<>());
        mockListasDeReproducao.add(lista2);

        when(listaDeReproducaoService.listAllListasDeReproducao()).thenReturn(mockListasDeReproducao);

        ResponseEntity<List<ListaDeReproducaoDTO>> response = listaDeReproducaoController.listAllListasDeReproducao("valid_token");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    public void testDeleteListaDeReproducao() {
        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);

        ListaDeReproducao mockListaDeReproducao = new ListaDeReproducao();
        mockListaDeReproducao.setNome("ABC");
        mockListaDeReproducao.setDescricao("AAA");
        mockListaDeReproducao.setMusicas(new ArrayList<>());

        when(listaDeReproducaoService.getListasDeReproducaoByNome(anyString())).thenReturn(Collections.singletonList(mockListaDeReproducao));

        ResponseEntity<Void> response = listaDeReproducaoController.deleteListaDeReproducao("ABC", anyString());

        verify(jwtTokenProvider).validateToken(anyString());

        verify(listaDeReproducaoService).getListasDeReproducaoByNome("ABC");

        verify(listaDeReproducaoService).deleteListaDeReproducao(mockListaDeReproducao.getId());

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testSearchListaDeReproducao() {
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY5NzgzMDE3NywiZXhwIjoxNjk3ODMzNzc3fQ.S2UcGhlGa7h4ekADSK6RiW0hDBeEEsEoeFE1seACNnk";

        when(jwtTokenProvider.validateToken(token)).thenReturn(true);

        ListaDeReproducao mockListaDeReproducao = new ListaDeReproducao();
        mockListaDeReproducao.setNome("ABC");
        mockListaDeReproducao.setDescricao("AAA");
        mockListaDeReproducao.setMusicas(new ArrayList<>());

        when(listaDeReproducaoService.getListaDeReproducaoByNome("ABC")).thenReturn(mockListaDeReproducao);

        ResponseEntity<ListaDeReproducaoDTO> response = listaDeReproducaoController.searchListaDeReproducao("ABC", token);

        verify(jwtTokenProvider).validateToken(token);
        verify(listaDeReproducaoService).getListaDeReproducaoByNome("ABC");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockListaDeReproducao.getNome(), response.getBody().getNome());
    }




}
