package com.wayster.vendas.Controller;

import com.wayster.vendas.Entity.Cliente;
import com.wayster.vendas.Repo.ClienteRepo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequestMapping("/api/clientes")
@RestController
public class ClienteController {

    private ClienteRepo clientes;

    public ClienteController(ClienteRepo clientes) {
        this.clientes = clientes;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save(@RequestBody Cliente cliente){
       return clientes.save(cliente);
    }
    
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Cliente getClienteById(@PathVariable Integer id){
      return clientes.findById(id).orElseThrow(() ->
              new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente nao encontrado"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        clientes.findById(id).map(cliente -> { clientes.delete(cliente); return cliente;}).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Cliente não encontrado"
        ));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody Cliente cliente){
        clientes.findById(id).map(clienteExistente ->
                {cliente.setId(clienteExistente.getId());
            clientes.save(cliente); return clienteExistente;})

                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não vai"));
    }

    @GetMapping
    public List<Cliente> find(Cliente filtro){
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Cliente> example = Example.of(filtro, matcher);
        return clientes.findAll(example);
    }

}