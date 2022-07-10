package main.rest.controller;

import main.domain.entity.Cliente;
import main.domain.repository.Cliente_repo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private Cliente_repo cliente_repo;


    @PostMapping("/api/clientes")
    @ResponseBody
    public ResponseEntity<Cliente> save(@RequestBody Cliente cliente){
        //save client
        Cliente c1 = cliente_repo.save(cliente);

        //return status OK
        return ResponseEntity.ok(c1);
    }


    @GetMapping("/api/clientes/{id}")
    @ResponseBody
    public ResponseEntity<Cliente> getClienteById(@PathVariable Integer id){
        //get idClient from repository method
        Optional<Cliente> cliente = cliente_repo.findById(id);

        //verify if client is present or true
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }



}
