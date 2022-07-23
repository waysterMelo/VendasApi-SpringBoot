package com.wayster.vendas.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacoesItemDoPedidoDto {

    private String descricao;
    private BigDecimal precoUnitario;
    private Integer quantidade;

}
