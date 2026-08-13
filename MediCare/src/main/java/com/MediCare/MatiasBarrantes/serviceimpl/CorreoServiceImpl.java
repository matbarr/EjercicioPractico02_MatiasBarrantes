package com.MediCare.MatiasBarrantes.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.MediCare.MatiasBarrantes.service.CorreoService;

@Service
public class CorreoServiceImpl implements CorreoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CorreoServiceImpl.class);

    private final JavaMailSender javaMailSender;

    @Value("${app.mail.from}")
    private String remitente;

    public CorreoServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void enviarCorreoBienvenida(String destinatario, String nombreUsuario) {
        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setFrom(remitente);
            mensaje.setTo(destinatario);
            mensaje.setSubject("Bienvenido a MediCare");
            mensaje.setText("Hola " + nombreUsuario + ",\n\nTu registro en MediCare fue exitoso.\n\nSaludos,\nEquipo MediCare");
            javaMailSender.send(mensaje);
        } catch (Exception exception) {
            LOGGER.warn("No se pudo enviar correo de bienvenida a {}: {}", destinatario, exception.getMessage());
        }
    }
}
