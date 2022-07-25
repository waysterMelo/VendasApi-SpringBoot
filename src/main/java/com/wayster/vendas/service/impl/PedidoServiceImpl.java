package com.wayster.vendas.service.impl;

import com.wayster.vendas.Dto.ItemDoPedidoDto;
import com.wayster.vendas.Dto.PedidoDto;
import com.wayster.vendas.Entity.*;
import com.wayster.vendas.Repo.ClienteRepo;
import com.wayster.vendas.Repo.ItemPedidoRepository;
import com.wayster.vendas.Repo.PedidosRepository;
import com.wayster.vendas.Repo.ProdutoRepository;
import com.wayster.vendas.exception.PedidoNaoEncontradoException;
import com.wayster.vendas.exception.RegraDeNegocioException;
import com.wayster.vendas.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidosRepository pedidosRepository;
    private final ClienteRepo clienteRepo;
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar( PedidoDto dto ) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clienteRepo
                .findById(idCliente)
                .orElseThrow(() -> new RegraDeNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDatapedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatusPedido(Status.REALIZADO);

        List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItens());
        pedidosRepository.save(pedido);
        itemPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);
        return pedido;
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemDoPedidoDto> items){
        if(items.isEmpty()){
            throw new RegraDeNegocioException("Não é possível realizar um pedido sem items.");
        }

        return items
                .stream()
                .map( dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtoRepository
                            .findById(idProduto)
                            .orElseThrow(
                                    () -> new RegraDeNegocioException(
                                            "Código de produto inválido: "+ idProduto
                                    ));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());

    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidosRepository.findByIdFetchItens(id);
    }

    @Override
    public void atualizaStatus(Integer id, Status status) {
        pedidosRepository.findById(id).map(pedido -> {
            pedido.setStatusPedido(status);
            return pedidosRepository.save(pedido);
        }).orElseThrow(PedidoNaoEncontradoException::new);
    }
}
