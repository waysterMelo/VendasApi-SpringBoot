package com.wayster.vendas.Dto;

import com.wayster.vendas.validation.NotEmptyList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoDto {

    @NotNull(message = "Informe o código do cliente.")
    private Integer cliente;

    @NotNull(message = "Campo Total do pedido é obrigatório.")
    private BigDecimal total;

    @NotEmptyList(message = "Pedido nao pode ser realizado sem itens")
    private List<ItemDoPedidoDto> itens;

}
