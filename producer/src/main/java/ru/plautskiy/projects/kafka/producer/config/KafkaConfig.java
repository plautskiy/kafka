package ru.plautskiy.projects.kafka.producer.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.*;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.plautskiy.projects.kafka.producer.beans.GenericMessage;

import java.util.Map;

@Configuration
@EnableKafka
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaConfig {

    @Autowired
    private KafkaProperties properties;

    @Autowired
    private ObjectProvider<RecordMessageConverter> messageConverterProvider;

    @Bean
    public KafkaTemplate<String, GenericMessage> jsonKafkaTemplate(ProducerFactory<String, GenericMessage> jsonProducerFactory) {

        KafkaTemplate<String, GenericMessage> kafkaTemplate = new KafkaTemplate<>(jsonProducerFactory);
//        RecordMessageConverter messageConverter = messageConverterProvider.getIfUnique();
//        if (messageConverter != null) {
//            kafkaTemplate.setMessageConverter(messageConverter);
//        }
        return kafkaTemplate;
    }

    @Bean
    public ProducerFactory<String, GenericMessage> jsonProducerFactory() {
        Map<String, Object> producerProperties = this.properties.buildProducerProperties();

        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());

        DefaultKafkaProducerFactory<String, GenericMessage> factory = new DefaultKafkaProducerFactory<>(producerProperties);

        String transactionIdPrefix = this.properties.getProducer().getTransactionIdPrefix();

        if (transactionIdPrefix != null) {
            factory.setTransactionIdPrefix(transactionIdPrefix);
        }
        return factory;
    }
}
