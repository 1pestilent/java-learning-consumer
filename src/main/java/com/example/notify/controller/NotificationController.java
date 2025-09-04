package com.example.notify.controller;


import com.example.notify.model.User;
import com.example.notify.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final EmailService emailService;


    public NotificationController(EmailService emailService) {
        this.emailService = emailService;
    }
    @PostMapping("/user-created")
    public ResponseEntity<String> sendUserCreatedNotification(@RequestBody User request) {
        try {

            emailService.sendUserCreatedNotification(request);
            return ResponseEntity.ok("Уведомление о создании пользователя отправлено");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Ошибка при отправке уведомления: " + e.getMessage());
        }
    }

    @PostMapping("/user-deleted")
    public ResponseEntity<String> sendUserDeletedNotification(@RequestBody String user_email) {
        try {
            emailService.sendUserDeletedNotification(user_email);
            return ResponseEntity.ok("Уведомление об удалении пользователя отправлено успешно");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Ошибка при отправке уведомления: " + e.getMessage());
        }
    }
}