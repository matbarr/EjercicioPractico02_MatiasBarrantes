package com.MediCare.MatiasBarrantes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MediCare.MatiasBarrantes.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByRolNombre(String nombreRol);

    long countByRolId(Long rolId);
}
