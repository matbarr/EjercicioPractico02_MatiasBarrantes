package com.MediCare.MatiasBarrantes.service;

import java.util.List;
import java.util.Optional;

import com.MediCare.MatiasBarrantes.domain.Usuario;

public interface UsuarioService {

    List<Usuario> listarTodos();

    Optional<Usuario> buscarPorId(Long id);

    Optional<Usuario> buscarPorEmail(String email);

    List<Usuario> buscarPorNombreRol(String nombreRol);

    Usuario guardar(Usuario usuario);

    Usuario registrar(Usuario usuario);

    void eliminarPorId(Long id);
}
