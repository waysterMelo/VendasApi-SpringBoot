package main.rest.controller;


import main.domain.entity.Produto;
import main.domain.repository.Produtos_repo;
import org.apache.coyote.Response;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private Produtos_repo repository;

    public ProdutoController(Produtos_repo repository){
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto save(@RequestBody Produto produto){
        return repository.save(produto);
    }


    @PutMapping({"id"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, Produto produto){
        repository.findById(id).map(p -> {p.setId(produto.getId());
            repository.save(produto);
            return produto; }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado"));

    }

    @DeleteMapping({"id"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        repository.findById(id).map(result -> {repository.delete(result); return Void.TYPE;}).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping({"id"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Produto getById(@PathVariable Integer id){
        return repository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No product was founded !"));
    }


    @GetMapping
    public List<Produto> find(Produto filtro){
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example ex = Example.of(filtro, exampleMatcher);
        return repository.findAll(ex);
    }

}
