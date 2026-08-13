package com.MediCare.MatiasBarrantes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MediCare.MatiasBarrantes.domain.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {

    Optional<Rol> findByNombre(String nombre);
}
