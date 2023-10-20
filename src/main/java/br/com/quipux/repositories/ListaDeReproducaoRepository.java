package br.com.quipux.repositories;

import br.com.quipux.entities.ListaDeReproducao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface ListaDeReproducaoRepository extends JpaRepository<ListaDeReproducao, Integer> {
    ListaDeReproducao findByNome(String nome);

    @Query("SELECT lr FROM ListaDeReproducao lr WHERE lr.nome = :nome")
    List<ListaDeReproducao> findAllByNome(@Param("nome") String nome);
}
