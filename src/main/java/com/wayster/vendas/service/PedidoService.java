package com.wayster.vendas.service;

import com.wayster.vendas.Dto.PedidoDto;
import com.wayster.vendas.Entity.Pedido;
import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDto pedidoDto);
    Optional<Pedido> obterPedidoCompleto(Integer id);
}
