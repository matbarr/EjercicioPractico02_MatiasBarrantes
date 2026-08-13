package com.MediCare.MatiasBarrantes.controllers;

import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.MediCare.MatiasBarrantes.domain.Rol;
import com.MediCare.MatiasBarrantes.domain.Usuario;
import com.MediCare.MatiasBarrantes.service.RolService;
import com.MediCare.MatiasBarrantes.service.UsuarioService;

@Controller
public class AuthController {

    private final UsuarioService usuarioService;
    private final RolService rolService;

    public AuthController(UsuarioService usuarioService, RolService rolService) {
        this.usuarioService = usuarioService;
        this.rolService = rolService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrar(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult, Model model) {
        if (usuarioService.buscarPorEmail(usuario.getEmail()).isPresent()) {
            bindingResult.rejectValue("email", "duplicado", "Este correo ya esta registrado");
        }

        Optional<Rol> rolPaciente = rolService.listarTodos().stream()
                .filter(rol -> "PACIENTE".equalsIgnoreCase(rol.getNombre()))
                .findFirst();

        if (rolPaciente.isEmpty()) {
            bindingResult.reject("rol", "No existe el rol PACIENTE en la base de datos");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("usuario", usuario);
            return "registro";
        }

        usuario.setRol(rolPaciente.orElseThrow());
        usuario.setActivo(Boolean.TRUE);
        usuarioService.registrar(usuario);

        return "redirect:/login?registroExitoso=true";
    }
}
