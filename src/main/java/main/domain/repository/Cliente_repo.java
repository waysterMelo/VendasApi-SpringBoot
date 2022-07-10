package main.domain.repository;

import main.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface Cliente_repo extends JpaRepository<Cliente, Integer> {


    @Query(value = "select c from Cliente c where c.nome like :nome")
    List<Cliente> findByNomeLike(@Param("nome") String nome);

    @Query(value = "delete from Cliente c where c.nome = :nome")
    @Modifying
    void deleteByNome(String nome);

    boolean existsByNome(String nome);

    @Query("select c from Cliente c left join fetch c.pedidos where c.id = :id")
    Cliente findClienteFetchPedido(@Param("id") Integer id);

}