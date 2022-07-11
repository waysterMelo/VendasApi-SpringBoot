package com.wayster.vendas.Controller;

import com.wayster.vendas.Entity.Cliente;
import com.wayster.vendas.Repo.ClienteRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/clientes")
@RestController
@AllArgsConstructor
@NoArgsConstructor
public class ClienteController {

    @Autowired
    private ClienteRepo clienteRepo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save(@RequestBody Cliente cliente){
       return clienteRepo.save(cliente);
    }


}