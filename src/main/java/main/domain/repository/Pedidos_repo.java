package main.domain.repository;

import main.domain.entity.Cliente;
import main.domain.entity.Pedido;
import main.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Pedidos_repo extends JpaRepository<Pedido, Integer> {


    List<Pedido> findByCliente(Cliente cliente);

}
