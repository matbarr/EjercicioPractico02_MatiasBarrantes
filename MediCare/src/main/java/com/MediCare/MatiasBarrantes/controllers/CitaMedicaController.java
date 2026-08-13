package com.MediCare.MatiasBarrantes.controllers;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.MediCare.MatiasBarrantes.domain.CitaMedica;
import com.MediCare.MatiasBarrantes.service.CitaMedicaService;

@Controller
@RequestMapping("/citas")
public class CitaMedicaController {

    private final CitaMedicaService citaMedicaService;

    public CitaMedicaController(CitaMedicaService citaMedicaService) {
        this.citaMedicaService = citaMedicaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("citas", citaMedicaService.listarTodas());
        return "citas/lista";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioNueva(Model model) {
        model.addAttribute("cita", new CitaMedica());
        return "citas/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("cita") CitaMedica citaMedica, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "citas/form";
        }
        citaMedicaService.guardar(citaMedica);
        return "redirect:/citas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        CitaMedica cita = citaMedicaService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"));
        model.addAttribute("cita", cita);
        return "citas/form";
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        CitaMedica cita = citaMedicaService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"));
        model.addAttribute("cita", cita);
        return "citas/detalle";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        citaMedicaService.eliminarPorId(id);
        return "redirect:/citas";
    }
}
