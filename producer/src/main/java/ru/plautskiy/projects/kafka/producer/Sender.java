package ru.plautskiy.projects.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.plautskiy.projects.kafka.producer.beans.GenericMessage;

import static ru.plautskiy.projects.kafka.producer.GenericProducerApp.TOPIC;

@Component
public class Sender {
    private static Logger logger = LoggerFactory.getLogger(Sender.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private KafkaTemplate<String, GenericMessage> jsonKafkaTemplate;



    public void send(final GenericMessage msg) {
        String msgToSend="";

        try {
            msgToSend = mapper.writeValueAsString(msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        ListenableFuture<SendResult<String, GenericMessage>> result = jsonKafkaTemplate.send(TOPIC, msg);

        result.addCallback(new ListenableFutureCallback<SendResult<String, GenericMessage>>() {

            @Override
            public void onSuccess(SendResult<String, GenericMessage> GenericMessageSendResult) {
                logger.info("Successful sent message" + msg + " to topic:" + TOPIC);
            }

            @Override
            public void onFailure(Throwable throwable) {
                logger.info("Failed sent message." + throwable.getMessage());
            }
        });


    }
}
