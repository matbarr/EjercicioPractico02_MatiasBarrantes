package com.MediCare.MatiasBarrantes.config;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String role = authority.getAuthority();
            if ("ROLE_ADMIN".equals(role)) {
                response.sendRedirect("/usuarios");
                return;
            }
            if ("ROLE_MEDICO".equals(role)) {
                response.sendRedirect("/citas");
                return;
            }
            if ("ROLE_PACIENTE".equals(role)) {
                response.sendRedirect("/citas");
                return;
            }
        }

        response.sendRedirect("/");
    }
}
