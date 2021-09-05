package main.rest.controller;

import main.domain.entity.Cliente;
import main.domain.repository.Cliente_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class ClienteController {

    @Autowired
    private Cliente_repo cliente_repo;

    ClienteController(Cliente_repo cliente_repo){
        this.cliente_repo = cliente_repo;
    }


    @GetMapping("/api/clientes/{id}")
    @ResponseBody
    public ResponseEntity<Cliente> getClienteById(@PathVariable Integer id){
        Optional<Cliente> cliente = cliente_repo.findById(id);

        if (cliente.isPresent()){
            return ResponseEntity.ok(cliente.get());
    }

        return ResponseEntity.notFound().build();
    }

}
