package com.MediCare.MatiasBarrantes.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.MediCare.MatiasBarrantes.domain.CitaMedica;

public interface CitaMedicaRepository extends JpaRepository<CitaMedica, Long> {

    List<CitaMedica> findByActiva(Boolean activa);

    List<CitaMedica> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);

    List<CitaMedica> findByEspecialidadContainingIgnoreCase(String especialidad);

    @Query("select count(c) from CitaMedica c where c.activa = true")
    long contarCitasActivas();
}
