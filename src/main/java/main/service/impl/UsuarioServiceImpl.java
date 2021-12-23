package main.service.impl;

import main.domain.entity.Usuario;
import main.domain.repository.UsuarioRepository;
import main.exception.SenhaInvalidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
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
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public UserDetails autenticar(Usuario usuario){
       UserDetails userDetails = loadUserByUsername(usuario.getLogin());
      boolean senhasBatem =  encoder.matches(usuario.getSenha(), userDetails.getPassword());
      if (senhasBatem){
          return userDetails;
      }

      throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin(username).orElseThrow( () -> new UsernameNotFoundException("Usuario nao encontrado no banco de dados"));

        String[] roles = usuario.isAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return User.builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles).build();

    }
}
