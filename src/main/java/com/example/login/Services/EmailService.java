package com.example.login.Services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void envoyerEmailSimple(String to, String sujet, String texte) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@votre-application.com"); // Mettez une adresse d'expéditeur
            message.setTo(to);
            message.setSubject(sujet);
            message.setText(texte);
            mailSender.send(message);
        } catch (Exception e) {
            // Loggez l'erreur de manière appropriée
            // Dans un vrai projet, on ne laisserait pas l'exception planter le processus
            throw new RuntimeException("Échec de l'envoi de l'email", e);
        }
    }
}