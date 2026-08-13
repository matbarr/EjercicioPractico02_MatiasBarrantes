package com.MediCare.MatiasBarrantes.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.MediCare.MatiasBarrantes.domain.Usuario;
import com.MediCare.MatiasBarrantes.repository.UsuarioRepository;
import com.MediCare.MatiasBarrantes.service.CorreoService;
import com.MediCare.MatiasBarrantes.service.UsuarioService;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final CorreoService correoService;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, CorreoService correoService, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.correoService = correoService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> buscarPorNombreRol(String nombreRol) {
        return usuarioRepository.findByRolNombre(nombreRol);
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        boolean esNuevo = usuario.getId() == null;
        usuario.setPassword(codificarSiEsPlano(usuario.getPassword()));
        Usuario guardado = usuarioRepository.save(usuario);
        if (esNuevo) {
            correoService.enviarCorreoBienvenida(guardado.getEmail(), guardado.getNombre());
        }
        return guardado;
    }

    @Override
    public Usuario registrar(Usuario usuario) {
        return guardar(usuario);
    }

    @Override
    public void eliminarPorId(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("No existe el usuario con correo: " + username));

        String rolNombre = usuario.getRol() != null ? usuario.getRol().getNombre() : "PACIENTE";
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + rolNombre);

        return User.withUsername(usuario.getEmail())
                .password(usuario.getPassword())
                .authorities(authority)
                .disabled(Boolean.FALSE.equals(usuario.getActivo()))
                .build();
    }

    private String codificarSiEsPlano(String password) {
        if (password == null || password.isBlank()) {
            return password;
        }
        if (password.startsWith("$2a$") || password.startsWith("$2b$") || password.startsWith("$2y$") || password.startsWith("{noop}")) {
            return password;
        }
        return passwordEncoder.encode(password);
    }
}
