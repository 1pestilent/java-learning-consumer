package com.example.notify.service;

import com.example.notify.model.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserConsumerService {

    private final EmailService emailService;

    public UserConsumerService(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "new-users")
    public void consumeNewUser(ConsumerRecord<String, User> record) {
        emailService.sendUserCreatedNotification(record.value());
    }

    @KafkaListener(topics = "deleted-users")
    public void consumeDeletedUser(ConsumerRecord<String, User> record) {
        emailService.sendUserDeletedNotification(record.value().getEmail());
    }
}
