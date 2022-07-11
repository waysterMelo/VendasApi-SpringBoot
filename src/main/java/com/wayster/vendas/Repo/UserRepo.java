package com.wayster.vendas.Repo;

import com.wayster.vendas.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {


}
