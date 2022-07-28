package com.wayster.vendas.service.impl;


import com.wayster.vendas.Entity.User;
import com.wayster.vendas.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       //procurar usuario pelo Login
        User user = userRepo.findByLogin(username).orElseThrow(()
                -> new UsernameNotFoundException("Usuario nao encontrado"));

        //verificar se o usuario e adm e atribuir roles
        String[] roles = user.isAdmin() ? new String[]{"ADMIN","USER"} : new String[]{"USER"};

        //retornar o usuario
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getLogin())
                .password(user.getSenha())
                .roles(roles)
                .build();

    }

    @Transactional
    public User salvar(User user){
        return userRepo.save(user);
    }


}
