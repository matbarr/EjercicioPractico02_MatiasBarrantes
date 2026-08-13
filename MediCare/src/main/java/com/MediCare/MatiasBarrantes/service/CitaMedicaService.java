package com.MediCare.MatiasBarrantes.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.MediCare.MatiasBarrantes.domain.CitaMedica;

public interface CitaMedicaService {

    List<CitaMedica> listarTodas();

    Optional<CitaMedica> buscarPorId(Long id);

    CitaMedica guardar(CitaMedica citaMedica);

    void eliminarPorId(Long id);

    List<CitaMedica> buscarPorEstado(Boolean activa);

    List<CitaMedica> buscarPorRangoFechas(LocalDate inicio, LocalDate fin);

    List<CitaMedica> buscarPorEspecialidadParcial(String especialidad);

    long contarCitasActivas();
}
