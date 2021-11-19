package main.rest.controller;

import main.domain.entity.Cliente;
import main.domain.repository.Cliente_repo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {


    private Cliente_repo cliente_repo;

    ClienteController(Cliente_repo cliente_repo){
        this.cliente_repo = cliente_repo;
    }


    @GetMapping("{id}")
    public Cliente getClienteById(@PathVariable Integer id){
        return cliente_repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save(@RequestBody @Valid Cliente cliente){
       return cliente_repo.save(cliente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        cliente_repo.findById(id).map(c -> { cliente_repo.delete(c); return c;}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable @Valid Integer id, @RequestBody Cliente cliente){
        cliente_repo.findById(id).map(clienteExistente -> { cliente.setId(clienteExistente.getId());
            cliente_repo.save(cliente);
            return clienteExistente;
        }).orElseThrow(()  -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


        @GetMapping
        public List<Cliente> find(Cliente filtro){
            ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
            Example example = Example.of(filtro, exampleMatcher);
            return cliente_repo.findAll();
        }


}
