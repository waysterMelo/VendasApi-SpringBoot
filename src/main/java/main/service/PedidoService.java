package main.service;

import main.domain.entity.Pedido;
import main.dto.PedidoDto;
import org.springframework.stereotype.Service;

@Service
public interface PedidoService {

    Pedido salvar (PedidoDto dto);
}
