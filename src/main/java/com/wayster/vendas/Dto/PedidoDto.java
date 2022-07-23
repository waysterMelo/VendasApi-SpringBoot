package com.wayster.vendas.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoDto {

    private Integer cliente;
    private BigDecimal total;
    private List<ItemDoPedidoDto> itens;
}
