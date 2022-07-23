package com.wayster.vendas.Controller;

import com.wayster.vendas.Dto.InformacoesItemDoPedidoDto;
import com.wayster.vendas.Dto.InformacoesPedidoDto;
import com.wayster.vendas.Dto.PedidoDto;
import com.wayster.vendas.Entity.ItemPedido;
import com.wayster.vendas.Entity.Pedido;
import com.wayster.vendas.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/pedidos")
@RestController
public class PedidoController {

    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody PedidoDto pedidoDto){
        Pedido pedido = pedidoService.salvar(pedidoDto);
        return pedido.getId();
    }

    @GetMapping("{id}")
    public InformacoesPedidoDto getById(@PathVariable Integer id){
        return pedidoService.obterPedidoCompleto(id).map(p -> pegarPedido(p))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido nao encontrado"));
    }

    public InformacoesPedidoDto pegarPedido(Pedido pedido){
        return InformacoesPedidoDto.builder()
                .codigo(pedido.getId())
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getCpf())
                .dataPedido(pedido.getDatapedido().format(DateTimeFormatter.ofPattern("dd/mm/yyyy")))
                .total(pedido.getTotal())
                .itens(pegarItensInfo(pedido.getItens()))
                .build();
    }

    private List<InformacoesItemDoPedidoDto> pegarItensInfo(List<ItemPedido> itens) {
        //verificar se a lista esta vazia
        if (itens.isEmpty()){
            return Collections.emptyList();
        }

        //mapear produto com pedido
        return itens.stream().map(itemPedido -> InformacoesItemDoPedidoDto.builder().descricao(itemPedido.getProduto().getDescricao())
                .descricao(itemPedido.getProduto().getDescricao()).precoUnitario(itemPedido.getProduto().getPreco())
                .quantidade(itemPedido.getQuantidade()).build()).collect(Collectors.toList());
    }

}
