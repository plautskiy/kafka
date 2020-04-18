package ru.plautskiy.projects.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import ru.plautskiy.projects.kafka.consumer.beans.Order;
import ru.plautskiy.projects.kafka.consumer.controller.MessageController;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import static ru.plautskiy.projects.kafka.consumer.GenericConsumerApp.TOPIC;

@Component
public class Receiver {

    private static Logger logger = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    private MessageController messageController;

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }



    @KafkaListener(topics = TOPIC, containerFactory = "jsonKafkaListenerContainerFactory")
    public void listenJsonMessages(ConsumerRecord<String, Order> cr, @Header(KafkaHeaders.GROUP_ID) String groupId) {
        logger.info(TOPIC + " Received a message for=" + groupId);
        logger.info(TOPIC + " Received a message with value=" + cr.value().toJson());

        SseEmitter latestEm = messageController.getLatestEmitter();

        try {
			latestEm.send(cr.value().toJson());
		} catch (IOException e) {
			latestEm.completeWithError(e);
		}
        latch.countDown();
    }


}
