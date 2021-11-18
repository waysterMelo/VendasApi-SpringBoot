package main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDto {

    private Integer cliente;
    private BigDecimal total;
    private List<ItemsPedido> items;


}
