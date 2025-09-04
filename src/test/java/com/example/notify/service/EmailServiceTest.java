package com.example.notify.service;

import com.example.notify.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(21L);
        testUser.setEmail("test@example.com");
        testUser.setUsername("Test User");
        testUser.setAge(22);
    }

    @Test
    void sendUserCreatedNotification_ShouldSendEmailSuccessfully() {
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        assertDoesNotThrow(() -> emailService.sendUserCreatedNotification(testUser));

        verify(mailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage sentMessage = messageCaptor.getValue();
        assertNotNull(sentMessage.getTo());
        assertEquals("test@example.com", sentMessage.getTo()[0]);
        assertEquals("Добро пожаловать!", sentMessage.getSubject());
        assertNotNull(sentMessage.getText());
        assertTrue(sentMessage.getText().contains("Ваш аккаунт на сайте был успешно создан"));
    }

    @Test
    void sendUserDeletedNotification_ShouldSendEmailSuccessfully() {
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        assertDoesNotThrow(() -> emailService.sendUserDeletedNotification(testUser.getEmail()));

        verify(mailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage sentMessage = messageCaptor.getValue();
        assertNotNull(sentMessage.getText());
        assertNotNull(sentMessage.getTo());
        assertEquals("test@example.com", sentMessage.getTo()[0]);
        assertEquals("Прощайте!", sentMessage.getSubject());
        assertTrue(sentMessage.getText().contains("Ваш аккаунт на сайте был удален"));
    }
}