package com.wayster.vendas.Controller;

import com.wayster.vendas.Dto.AtualizacaoStatusDoPedido;
import com.wayster.vendas.Dto.InformacoesItemDoPedidoDto;
import com.wayster.vendas.Dto.InformacoesPedidoDto;
import com.wayster.vendas.Dto.PedidoDto;
import com.wayster.vendas.Entity.ItemPedido;
import com.wayster.vendas.Entity.Pedido;
import com.wayster.vendas.Entity.Status;
import com.wayster.vendas.Repo.PedidosRepository;
import com.wayster.vendas.service.PedidoService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/pedidos")
@RestController
public class PedidoController {

    private PedidoService pedidoService;
    private PedidosRepository pedidosRepository;

    public PedidoController(PedidoService pedidoService, PedidosRepository pedidosRepository) {
        this.pedidoService = pedidoService;
        this.pedidosRepository = pedidosRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody @Valid PedidoDto pedidoDto){
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
                .dataPedido(pedido.getDatapedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .total(pedido.getTotal())
                .status(pedido.getStatusPedido().name())
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

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable Integer id, @RequestBody AtualizacaoStatusDoPedido atualizacaoStatusDoPedido){
        String novoStatus = atualizacaoStatusDoPedido.getNovoStatus();
         pedidoService.atualizaStatus(id, Status.valueOf(novoStatus));
    }



}
