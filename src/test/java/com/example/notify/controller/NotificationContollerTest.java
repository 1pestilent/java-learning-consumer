package com.example.notify.controller;

import com.example.notify.model.User;
import com.example.notify.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private NotificationController notificationController;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(11L);
        testUser.setEmail("test@example.com");
        testUser.setUsername("test");
        testUser.setAge(22);
    }

    @Test
    void sendUserCreatedNotificationTest() {
        ResponseEntity<String> response = notificationController.sendUserCreatedNotification(testUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Уведомление о создании пользователя отправлено", response.getBody());
        verify(emailService, times(1)).sendUserCreatedNotification(any(User.class));
    }

    @Test
    void sendUserDeletedNotificationTest() {

        ResponseEntity<String> response = notificationController.sendUserDeletedNotification(testUser.getEmail());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Уведомление об удалении пользователя отправлено успешно", response.getBody());
        verify(emailService, times(1)).sendUserDeletedNotification(any());
    }
}