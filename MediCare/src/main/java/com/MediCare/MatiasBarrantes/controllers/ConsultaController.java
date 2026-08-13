package com.MediCare.MatiasBarrantes.controllers;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.MediCare.MatiasBarrantes.service.CitaMedicaService;
import com.MediCare.MatiasBarrantes.service.UsuarioService;

@Controller
@RequestMapping("/consultas")
public class ConsultaController {

    private final CitaMedicaService citaMedicaService;
    private final UsuarioService usuarioService;

    public ConsultaController(CitaMedicaService citaMedicaService, UsuarioService usuarioService) {
        this.citaMedicaService = citaMedicaService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String consultas() {
        return "consultas/index";
    }

    @GetMapping("/citas-estado")
    public String buscarPorEstado(@RequestParam Boolean activa, Model model) {
        model.addAttribute("tituloResultado", "Citas por estado");
        model.addAttribute("citasResultado", citaMedicaService.buscarPorEstado(activa));
        return "consultas/index";
    }

    @GetMapping("/citas-rango")
    public String buscarPorRango(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin,
            Model model) {
        model.addAttribute("tituloResultado", "Citas por rango de fechas");
        model.addAttribute("citasResultado", citaMedicaService.buscarPorRangoFechas(inicio, fin));
        return "consultas/index";
    }

    @GetMapping("/citas-especialidad")
    public String buscarPorEspecialidad(@RequestParam String especialidad, Model model) {
        model.addAttribute("tituloResultado", "Citas por especialidad parcial");
        model.addAttribute("citasResultado", citaMedicaService.buscarPorEspecialidadParcial(especialidad));
        return "consultas/index";
    }

    @GetMapping("/usuarios-rol")
    public String buscarUsuariosPorRol(@RequestParam String rol, Model model) {
        model.addAttribute("tituloUsuarios", "Usuarios por rol");
        model.addAttribute("usuariosResultado", usuarioService.buscarPorNombreRol(rol));
        return "consultas/index";
    }

    @GetMapping("/conteo-citas-activas")
    public String contarCitasActivas(Model model) {
        model.addAttribute("totalCitasActivas", citaMedicaService.contarCitasActivas());
        return "consultas/index";
    }
}
