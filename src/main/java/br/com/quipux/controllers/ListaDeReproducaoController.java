package br.com.quipux.controllers;

import br.com.quipux.dto.ListaDeReproducaoDTO;
import br.com.quipux.dto.MusicaDTO;
import br.com.quipux.entities.ListaDeReproducao;
import br.com.quipux.entities.Musica;
import br.com.quipux.security.JwtTokenProvider;
import br.com.quipux.services.ListaDeReproducaoService;
import br.com.quipux.services.MusicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@RestController
@RequestMapping("/lists")
public class ListaDeReproducaoController {
    private final ListaDeReproducaoService listaDeReproducaoService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MusicaService musicaService;


    @Autowired
    public ListaDeReproducaoController(ListaDeReproducaoService listaDeReproducaoService, JwtTokenProvider jwtTokenProvider, MusicaService musicaService) {
        this.listaDeReproducaoService = listaDeReproducaoService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.musicaService = musicaService;
    }

    @PostMapping
    public ResponseEntity<ListaDeReproducao> adicionarNovaListaDeReproducao(
            @RequestBody ListaDeReproducaoDTO playlistRequest,
            @RequestHeader("Authorization") String token) {

        if (!isValidToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String nome = playlistRequest.getNome();
        String descricao = playlistRequest.getDescricao();
        List<Integer> musicasIds = playlistRequest.getMusicasIds();

        if (nome == null || nome.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        List<Musica> musicas = musicaService.getMusicaByIds(musicasIds);

        if (musicas.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        ListaDeReproducao listaDeReproducao = new ListaDeReproducao();
        listaDeReproducao.setNome(nome);
        listaDeReproducao.setDescricao(descricao);
        listaDeReproducao.setMusicas(musicas);

        ListaDeReproducao listaDeReproducaoSalva = listaDeReproducaoService.saveListaDeReproducao(listaDeReproducao);

        return ResponseEntity.status(HttpStatus.CREATED).body(listaDeReproducaoSalva);
    }

    @GetMapping
    public ResponseEntity<List<ListaDeReproducaoDTO>> listAllListasDeReproducao(@RequestHeader("Authorization") String token) {

        List<ListaDeReproducao> listasDeReproducao = listaDeReproducaoService.listAllListasDeReproducao();

        List<ListaDeReproducaoDTO> listaDeReproducaoDTOs = new ArrayList<>();

        for (ListaDeReproducao lista : listasDeReproducao) {
            ListaDeReproducaoDTO dto = new ListaDeReproducaoDTO();
            dto.setNome(lista.getNome());
            dto.setDescricao(lista.getDescricao());

            List<MusicaDTO> musicaDTOs = new ArrayList<>();
            for (Musica musica : lista.getMusicas()) {
                MusicaDTO musicaDTO = new MusicaDTO();
                musicaDTO.setTitulo(musica.getTitulo());
                musicaDTO.setArtista(musica.getArtista());
                musicaDTO.setAlbum(musica.getAlbum());
                musicaDTO.setAno(musica.getAno());
                musicaDTO.setGenero(musica.getGenero());
                musicaDTOs.add(musicaDTO);
            }

            dto.setMusicas(musicaDTOs);

            listaDeReproducaoDTOs.add(dto);
        }

        return ResponseEntity.ok(listaDeReproducaoDTOs);
    }

    @DeleteMapping("/{listName}")
    public ResponseEntity<Void> deleteListaDeReproducao(@PathVariable String listName, @RequestHeader("Authorization") String token) {
        if (!isValidToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<ListaDeReproducao> listasDeReproducao = listaDeReproducaoService.getListasDeReproducaoByNome(listName);

        if (listasDeReproducao.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ListaDeReproducao listaDeReproducao = listasDeReproducao.get(0);

        listaDeReproducaoService.deleteListaDeReproducao(listaDeReproducao.getId());
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/search/{titulo}")
    public ResponseEntity<ListaDeReproducaoDTO> searchListaDeReproducao(@PathVariable String titulo, @RequestHeader("Authorization") String token) {
        if (!isValidToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        ListaDeReproducao listaDeReproducao = listaDeReproducaoService.getListaDeReproducaoByNome(titulo);

        if (listaDeReproducao == null) {
            return ResponseEntity.notFound().build();
        }

        ListaDeReproducaoDTO listaDeReproducaoDTO = new ListaDeReproducaoDTO();
        listaDeReproducaoDTO.setNome(listaDeReproducao.getNome());
        listaDeReproducaoDTO.setDescricao(listaDeReproducao.getDescricao());

        List<MusicaDTO> musicaDTOs = new ArrayList<>();
        for (Musica musica : listaDeReproducao.getMusicas()) {
            MusicaDTO musicaDTO = new MusicaDTO();
            musicaDTO.setTitulo(musica.getTitulo());
            musicaDTO.setArtista(musica.getArtista());
            musicaDTO.setAlbum(musica.getAlbum());
            musicaDTO.setAno(musica.getAno());
            musicaDTO.setGenero(musica.getGenero());
            musicaDTOs.add(musicaDTO);
        }

        listaDeReproducaoDTO.setMusicas(musicaDTOs);

        return ResponseEntity.ok(listaDeReproducaoDTO);
    }


    private boolean isValidToken(String token) {
        return token != null && jwtTokenProvider.validateToken(token);
    }

}
