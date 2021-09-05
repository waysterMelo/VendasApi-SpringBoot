package main;

import main.domain.entity.Cliente;
import main.domain.repository.Cliente_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class VendasApp {


    @Bean
    public CommandLineRunner commandLineRunner(@Autowired Cliente_repo cliente_repo){
        return args -> {
            Cliente c = new Cliente(null, "Wayster");
            cliente_repo.save(c);
        };
    }


    public static void main(String[] args) {
        SpringApplication.run(VendasApp.class, args);

    }
}
