package com.MediCare.MatiasBarrantes.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.MediCare.MatiasBarrantes.domain.Usuario;
import com.MediCare.MatiasBarrantes.repository.UsuarioRepository;

@Configuration
public class PasswordMigrationConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordMigrationConfig.class);

    @Bean
    CommandLineRunner migrarContrasenasPlanas(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            try {
                for (Usuario usuario : usuarioRepository.findAll()) {
                    String password = usuario.getPassword();
                    if (password == null || password.isBlank()) {
                        continue;
                    }
                    if (password.startsWith("$2a$") || password.startsWith("$2b$") || password.startsWith("$2y$")
                            || password.startsWith("{noop}")) {
                        continue;
                    }
                    usuario.setPassword(passwordEncoder.encode(password));
                    usuarioRepository.save(usuario);
                }
            } catch (Exception exception) {
                LOGGER.warn("Se omite migracion inicial de contrasenas: {}", exception.getMessage());
            }
        };
    }
}
