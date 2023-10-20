package br.com.quipux.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Musica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    private String artista;
    private String album;
    private int ano;
    private String genero;

    @JsonIgnore
    @ManyToMany(mappedBy = "musicas")
    private List<ListaDeReproducao> listasDeReproducao;
}