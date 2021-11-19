package main.rest.controller;

import main.domain.entity.Item_pedido;
import main.domain.entity.Pedido;
import main.domain.entity.StatusPedido;
import main.dto.*;
import main.exception.RegraDeNegocioException;
import main.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService){
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody @Valid PedidoDto dto){
        Pedido pedido = pedidoService.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id){
        return pedidoService.obterPedidoCompleto(id).map( p -> converter(p)).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Pedido nao encontrado"));
    }

    private InformacoesPedidoDTO converter(Pedido pedido){
       return InformacoesPedidoDTO.builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDatapedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
               .status(pedido.getStatusPedido().name())
                .items(converter(pedido.getItens())).build();
    };

    private List<InformacaoItemPedidoDTO> converter(List<Item_pedido> itens){
        if(CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }
        return itens.stream().map(
                item -> InformacaoItemPedidoDTO
                        .builder().descricaoProduto(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .build()
        ).collect(Collectors.toList());
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void updateStatus(@RequestBody @Valid AtualizacaoStatusPedidoDTO dto, @PathVariable Integer id){
        String novoStatus = dto.getNovoStatus();
        pedidoService.atualizarStatus(id, StatusPedido.valueOf(novoStatus));
    }
}
