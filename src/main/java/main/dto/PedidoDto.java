package main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDto {

    @NotNull(message = "Informe o codigo do clinte")
    private Integer cliente;

    @NotNull(message = "Campo total é obrigatorio")
    private BigDecimal total;

    private List<ItemsPedido> items;
}
