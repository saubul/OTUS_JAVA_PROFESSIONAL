package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.producer.MessageProducer;

import static ru.otus.config.ActiveMQConfig.QUEUE_NAME;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MessageController {

    private final MessageProducer messageProducer;

    @PostMapping("/message")
    public ResponseEntity<String> sendMessage(@RequestBody String message) {
        messageProducer.sendMessage(QUEUE_NAME, message);
        return ResponseEntity.ok("OK");
    }

}
