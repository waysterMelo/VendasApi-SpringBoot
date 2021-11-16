package main.rest.controller;

import main.domain.entity.Pedido;
import main.dto.PedidoDto;
import main.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService){
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody PedidoDto dto){
        Pedido pedido = pedidoService.salvar(dto);
        return pedido.getId();
    }

}
