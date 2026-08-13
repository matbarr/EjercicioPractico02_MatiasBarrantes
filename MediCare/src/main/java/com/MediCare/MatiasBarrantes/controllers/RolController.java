package com.MediCare.MatiasBarrantes.controllers;

import jakarta.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.MediCare.MatiasBarrantes.domain.Rol;
import com.MediCare.MatiasBarrantes.repository.UsuarioRepository;
import com.MediCare.MatiasBarrantes.service.RolService;

@Controller
@RequestMapping("/roles")
public class RolController {

    private final RolService rolService;
    private final UsuarioRepository usuarioRepository;

    public RolController(RolService rolService, UsuarioRepository usuarioRepository) {
        this.rolService = rolService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("roles", rolService.listarTodos());
        return "roles/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("rol", new Rol());
        return "roles/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("rol") Rol rol, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "roles/form";
        }
        rolService.guardar(rol);
        return "redirect:/roles";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Rol rol = rolService.buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));
        model.addAttribute("rol", rol);
        return "roles/form";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        long usuariosAsociados = usuarioRepository.countByRolId(id);
        if (usuariosAsociados > 0) {
            redirectAttributes.addFlashAttribute("error", "No se puede eliminar el rol porque tiene usuarios asociados.");
            return "redirect:/roles";
        }

        try {
            rolService.eliminarPorId(id);
            redirectAttributes.addFlashAttribute("exito", "Rol eliminado correctamente.");
        } catch (DataIntegrityViolationException exception) {
            redirectAttributes.addFlashAttribute("error", "No se puede eliminar el rol porque esta siendo utilizado.");
        }
        return "redirect:/roles";
    }
}
