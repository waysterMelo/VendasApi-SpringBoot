package com.wayster.vendas.Controller;

import com.wayster.vendas.Entity.Produto;
import com.wayster.vendas.Repo.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@AllArgsConstructor
public class ProdutoController {

    private ProdutoRepository produtoRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto save(@RequestBody Produto produto){
        return produtoRepository.save(produto);
    }

    @GetMapping
    public List<Produto> findAll(Produto filtro){
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Produto> example = Example.of(filtro, matcher);
        return produtoRepository.findAll(example);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void delete(@PathVariable Integer id){
        produtoRepository.findById(id).map(produto -> { produtoRepository.delete(produto);
            return produto;}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "Produto não econtrado"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Produto update(@PathVariable Integer id, @RequestBody Produto produto){
       return produtoRepository.findById(id).map(produtoExistente -> {produto.setId(produtoExistente.getId()); produtoRepository.save(produto);
           return produto;})
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Produto getProdutoById(@PathVariable Integer id){
       return produtoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado"));
    }

}
