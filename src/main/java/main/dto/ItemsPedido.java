package main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.domain.entity.Pedido;
import main.domain.entity.Produto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemsPedido {

    private Integer produto;
    private Integer quantidade;
    private Pedido pedido;



}
