package com.MediCare.MatiasBarrantes.service;

import java.util.List;
import java.util.Optional;

import com.MediCare.MatiasBarrantes.domain.Rol;

public interface RolService {

    List<Rol> listarTodos();

    Optional<Rol> buscarPorId(Long id);

    Rol guardar(Rol rol);

    void eliminarPorId(Long id);
}
