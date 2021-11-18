package main.service.impl;

import lombok.RequiredArgsConstructor;
import main.domain.entity.*;
import main.domain.repository.Cliente_repo;
import main.domain.repository.Item_pedido_repo;
import main.domain.repository.Pedidos_repo;
import main.domain.repository.Produtos_repo;
import main.dto.ItemsPedido;
import main.dto.PedidoDto;
import main.exception.PedidoNaoEncontradoException;
import main.exception.RegraDeNegocioException;
import main.service.PedidoService;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos_repo repo;
    private final Cliente_repo clientes_repo;
    private final Produtos_repo produtos_repo;
    private final Item_pedido_repo item_pedido_repo;



    @Override
    @Transactional
    public Pedido salvar(PedidoDto dto) {
       //pega o id do cliente
        Integer idCliente = dto.getCliente();
       //retorna ele do banco de dados e armazena as info em cliente
        Cliente cliente = clientes_repo.findById(idCliente).orElseThrow(() -> new RegraDeNegocioException("Código de cliente inválido"));

        //realizar um novo pedido e popular as info
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDatapedido(LocalDate.now());
        pedido.setTotal(dto.getTotal());
        pedido.setStatusPedido(StatusPedido.REALIZADO);

        //
        List<Item_pedido> item_pedidos = converterItems(pedido, dto.getItems());
        repo.save(pedido);
        item_pedido_repo.saveAll(item_pedidos);
        pedido.setItens(item_pedidos);
        return pedido;
    }

    private  List<Item_pedido> converterItems(Pedido pedido, List<ItemsPedido> items) {
        if (items.isEmpty()) {
            throw new RegraDeNegocioException("Não é possível realizar um pedido sem items");
        }
    return items.stream().map(dto -> {
        Integer idProduto = dto.getProduto();
        Produto produto = produtos_repo.findById(idProduto).orElseThrow(() -> new RegraDeNegocioException("Código de produto inválido: "+ idProduto));

        Item_pedido item_pedido = new Item_pedido();
        item_pedido.setPedido(pedido);
        item_pedido.setQuantidade(dto.getQuantidade());
        item_pedido.setProduto(produto);
        return item_pedido;

    }).collect(Collectors.toList());
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repo.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizarStatus(Integer id, StatusPedido statusPedido) {
        repo.findById(id).map( p -> {
            p.setStatusPedido(statusPedido);
            return repo.save(p);
        }).orElseThrow( () -> new PedidoNaoEncontradoException());
    }
}
