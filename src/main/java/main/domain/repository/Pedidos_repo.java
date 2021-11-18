package main.domain.repository;

import main.domain.entity.Cliente;
import main.domain.entity.Pedido;
import main.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface Pedidos_repo extends JpaRepository<Pedido, Integer> {


    List<Pedido> findByCliente(Cliente cliente);


    @Query("select p from Pedido p left join fetch p.itens where p.id = :id ")
    Optional<Pedido> findByIdFetchItens(Integer id);

}
