package com.MediCare.MatiasBarrantes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomLoginSuccessHandler customLoginSuccessHandler;

    public SecurityConfig(CustomLoginSuccessHandler customLoginSuccessHandler) {
        this.customLoginSuccessHandler = customLoginSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/login", "/registro", "/registro/**", "/error")
                        .permitAll()
                        .requestMatchers("/usuarios/**", "/roles/**").hasRole("ADMIN")
                        .requestMatchers("/citas/nueva", "/citas/guardar", "/citas/editar/**", "/citas/eliminar/**")
                        .hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers("/consultas/**").hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers("/citas/**").hasAnyRole("ADMIN", "MEDICO", "PACIENTE")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(customLoginSuccessHandler)
                        .failureUrl("/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll())
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/acceso-denegado"))
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
}
