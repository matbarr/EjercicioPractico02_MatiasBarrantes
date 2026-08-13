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

import com.MediCare.MatiasBarrantes.domain.Rol;
import com.MediCare.MatiasBarrantes.domain.Usuario;
import com.MediCare.MatiasBarrantes.service.RolService;
import com.MediCare.MatiasBarrantes.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final RolService rolService;

    public UsuarioController(UsuarioService usuarioService, RolService rolService) {
        this.usuarioService = usuarioService;
        this.rolService = rolService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "usuarios/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        Usuario usuario = new Usuario();
        usuario.setRol(new Rol());
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolService.listarTodos());
        return "usuarios/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult, Model model) {
        if (usuarioService.buscarPorEmail(usuario.getEmail()).isPresent() && usuario.getId() == null) {
            bindingResult.rejectValue("email", "duplicado", "Este correo ya esta registrado");
        }

        if (usuario.getRol() == null || usuario.getRol().getId() == null) {
            bindingResult.rejectValue("rol", "requerido", "Debe seleccionar un rol");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", rolService.listarTodos());
            return "usuarios/form";
        }

        usuario.setRol(rolService.buscarPorId(usuario.getRol().getId())
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado")));

        if (usuario.getId() != null) {
            usuarioService.buscarPorId(usuario.getId()).ifPresent(usuarioExistente -> {
                if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
                    usuario.setPassword(usuarioExistente.getPassword());
                }
                if (usuario.getFechaCreacion() == null) {
                    usuario.setFechaCreacion(usuarioExistente.getFechaCreacion());
                }
            });
        }

        usuarioService.guardar(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        if (usuario.getRol() == null) {
            usuario.setRol(new Rol());
        }
        usuario.setPassword("");
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolService.listarTodos());
        return "usuarios/form";
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        model.addAttribute("usuario", usuario);
        return "usuarios/detalle";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        usuarioService.eliminarPorId(id);
        return "redirect:/usuarios";
    }
}
