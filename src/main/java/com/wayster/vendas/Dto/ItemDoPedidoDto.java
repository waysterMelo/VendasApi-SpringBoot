package com.wayster.vendas.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDoPedidoDto {

    private Integer produto;
    private Integer quantidade;


}
