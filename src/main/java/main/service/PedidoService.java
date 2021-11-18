package main.service;

import main.domain.entity.Pedido;
import main.dto.PedidoDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface PedidoService {

    Pedido salvar (PedidoDto dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);
}
