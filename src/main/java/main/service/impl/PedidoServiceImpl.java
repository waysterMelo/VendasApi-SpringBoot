package main.service.impl;

import lombok.RequiredArgsConstructor;
import main.domain.entity.Cliente;
import main.domain.entity.Item_pedido;
import main.domain.entity.Pedido;
import main.domain.entity.Produto;
import main.domain.repository.Cliente_repo;
import main.domain.repository.Item_pedido_repo;
import main.domain.repository.Pedidos_repo;
import main.domain.repository.Produtos_repo;
import main.dto.ItemsPedido;
import main.dto.PedidoDto;
import main.exception.RegraDeNegocioException;
import main.service.PedidoService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos_repo repo;
    private final Cliente_repo clientes_repo;
    private final Produtos_repo produtos_repo;
    private final Item_pedido_repo item_pedido_repo;



    @Override
    public Pedido salvar(PedidoDto dto) {
        Integer id = dto.getCliente();
        Cliente cliente = clientes_repo.findById(id).orElseThrow( () -> new RegraDeNegocioException("Codigo de cliente invalido"));

        Pedido pedido = new Pedido();
       pedido.setTotal(dto.getTotal());
       pedido.setDatapedido(LocalDate.now());
       pedido.setCliente(cliente);

       List<ItemsPedido> itemsPedido = converterItems(pedido, dto.getItems());
       repo.save(pedido);
       item_pedido_repo.saveAll(itemsPedido);
       return null;
    }


    private  List<ItemsPedido> converterItems(Pedido pedido, List<ItemsPedido> items){
            if(items.isEmpty()){
                throw new RegraDeNegocioException("Nao e possivel realizar um pedido sem item");
            }
            return items.stream().map(dto -> {
                Integer idProduto = dto.getProduto();
                Produto produto = produtos_repo.findById(idProduto).orElseThrow(() -> new RegraDeNegocioException("Codigo de produto invalido:" + idProduto));
                ItemsPedido itemsPedido = new ItemsPedido();
            itemsPedido.setQuantidade(dto.getQuantidade());
            itemsPedido.setPedido(pedido);
            itemsPedido.setProduto(produto.getId());
            return itemsPedido;
            }).collect(Collectors.toList());

    }
}
