package com.wayster.vendas.Repo;

import com.wayster.vendas.Entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepo extends JpaRepository<Cliente, Integer> {
}
