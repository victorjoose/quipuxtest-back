package br.com.quipux.dto;
import java.util.List;

import lombok.Data;

@Data
public class ListaDeReproducaoDTO {
    private String nome;
    private String descricao;
    private List<Integer> musicasIds;
    private List<MusicaDTO> musicas;
}
