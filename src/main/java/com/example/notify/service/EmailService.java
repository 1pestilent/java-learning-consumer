package com.example.notify.service;


import com.example.notify.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendUserCreatedNotification(User user) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(username);
            message.setTo(user.getEmail());
            message.setSubject("Добро пожаловать!");
            message.setText("Здравствуйте! Ваш аккаунт на сайте был успешно создан.\n");
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось отправить email", e);
        }
    }

    public void sendUserDeletedNotification(User user) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(username);
            message.setTo(user.getEmail());
            message.setSubject("Прощайте!");
            message.setText("Здравствуйте! Ваш аккаунт на сайте был удален.\n");
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось отправить email", e);
        }
    }
}
