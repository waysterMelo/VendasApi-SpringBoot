package com.wayster.vendas.Repo;

import com.wayster.vendas.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {


    Optional<User> findByLogin(String login);
}
