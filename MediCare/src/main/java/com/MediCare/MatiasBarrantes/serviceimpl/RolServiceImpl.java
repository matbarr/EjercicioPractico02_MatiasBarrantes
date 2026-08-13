package com.MediCare.MatiasBarrantes.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.MediCare.MatiasBarrantes.domain.Rol;
import com.MediCare.MatiasBarrantes.repository.RolRepository;
import com.MediCare.MatiasBarrantes.service.RolService;

@Service
@Transactional
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Rol> listarTodos() {
        return rolRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Rol> buscarPorId(Long id) {
        return rolRepository.findById(id);
    }

    @Override
    public Rol guardar(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public void eliminarPorId(Long id) {
        rolRepository.deleteById(id);
    }
}
