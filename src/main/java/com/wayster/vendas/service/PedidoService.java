package com.wayster.vendas.service;

import com.wayster.vendas.Dto.PedidoDto;
import com.wayster.vendas.Entity.Pedido;
import com.wayster.vendas.Entity.Status;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDto pedidoDto);
    Optional<Pedido> obterPedidoCompleto(Integer id);

    void atualizaStatus(Integer id, Status status);
}
