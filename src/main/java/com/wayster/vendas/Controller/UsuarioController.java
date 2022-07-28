package com.wayster.vendas.Controller;

import com.wayster.vendas.Dto.CredenciaisDto;
import com.wayster.vendas.Dto.TokenDto;
import com.wayster.vendas.Entity.User;
import com.wayster.vendas.exception.SenhaInvalidaException;
import com.wayster.vendas.security.jwt.JwtService;
import com.wayster.vendas.service.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User salvar(@RequestBody @Valid User user){
        String key = passwordEncoder.encode(user.getSenha());
        user.setSenha(key);
        return usuarioService.salvar(user);
    }

    @PostMapping("/auth")
    public TokenDto autenticar(@RequestBody CredenciaisDto credenciaisDto){
        try {
            User usuario = User.builder().login(credenciaisDto.getLogin()).senha(credenciaisDto.getSenha()).build();
            UserDetails userDetails = usuarioService.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDto(usuario.getLogin(), token);
        }catch (UsernameNotFoundException | SenhaInvalidaException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
