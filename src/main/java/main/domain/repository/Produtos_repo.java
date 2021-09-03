package main.domain.repository;

import main.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos_repo extends JpaRepository<Produto, Integer> {



}
