package main;

import main.domain.entity.Cliente;
import main.domain.entity.Pedido;
import main.domain.repository.Cliente_repo;
import main.domain.repository.Pedidos_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@RestController
public class VendasApp {

    @Bean
    public CommandLineRunner init(@Autowired Cliente_repo cliente, @Autowired Pedidos_repo pedido){

        return args -> {

            System.out.println("Salvando ...");
            Cliente cliente1  = new Cliente("Wayster");
            cliente.save(cliente1);

            Pedido p = new Pedido();
            p.setCliente(cliente1);
            p.setDatapedido(LocalDate.now());
            p.setTotal(BigDecimal.valueOf(100));
            pedido.save(p);


//           Cliente cliente_com_pedido = cliente.findClienteFetchPedido(cliente1.getId());
//            System.out.println(cliente1);
//            System.out.println(cliente_com_pedido.getPedidos());

            pedido.findByCliente(cliente1).forEach(System.out::println);

        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApp.class, args);

    }
}
