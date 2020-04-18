package ru.plautskiy.projects.kafka.producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.plautskiy.projects.kafka.producer.Sender;
import ru.plautskiy.projects.kafka.producer.beans.GenericMessage;

@RestController
public class GenericController {
    @Autowired
    private Sender sender;

    @PostMapping("/wfm/messages/json")
    public ResponseEntity<String> sendJsonMessage(@RequestBody GenericMessage genericMessage) {
        sender.send(genericMessage);
        return ResponseEntity.ok("Message sent to Kafka: " + genericMessage);
    }
}
