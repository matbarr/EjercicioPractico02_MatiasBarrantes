package com.MediCare.MatiasBarrantes.serviceimpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.MediCare.MatiasBarrantes.domain.CitaMedica;
import com.MediCare.MatiasBarrantes.repository.CitaMedicaRepository;
import com.MediCare.MatiasBarrantes.service.CitaMedicaService;

@Service
@Transactional
public class CitaMedicaServiceImpl implements CitaMedicaService {

    private final CitaMedicaRepository citaMedicaRepository;

    public CitaMedicaServiceImpl(CitaMedicaRepository citaMedicaRepository) {
        this.citaMedicaRepository = citaMedicaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaMedica> listarTodas() {
        return citaMedicaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CitaMedica> buscarPorId(Long id) {
        return citaMedicaRepository.findById(id);
    }

    @Override
    public CitaMedica guardar(CitaMedica citaMedica) {
        return citaMedicaRepository.save(citaMedica);
    }

    @Override
    public void eliminarPorId(Long id) {
        citaMedicaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaMedica> buscarPorEstado(Boolean activa) {
        return citaMedicaRepository.findByActiva(activa);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaMedica> buscarPorRangoFechas(LocalDate inicio, LocalDate fin) {
        return citaMedicaRepository.findByFechaBetween(inicio, fin);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaMedica> buscarPorEspecialidadParcial(String especialidad) {
        return citaMedicaRepository.findByEspecialidadContainingIgnoreCase(especialidad);
    }

    @Override
    @Transactional(readOnly = true)
    public long contarCitasActivas() {
        return citaMedicaRepository.contarCitasActivas();
    }
}
