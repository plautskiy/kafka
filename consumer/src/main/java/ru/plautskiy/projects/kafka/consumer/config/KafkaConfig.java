package ru.plautskiy.projects.kafka.consumer.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.plautskiy.projects.kafka.consumer.beans.Order;

import java.util.Map;

@Configuration
@EnableKafka
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaConfig {

    @Autowired
    private KafkaProperties properties;


    @Bean
    public ConsumerFactory<String, Order> jsonKafkaConsumerFactory() {
        JsonDeserializer<Order> orderJsonDeserializer = new JsonDeserializer<>(Order.class);
        orderJsonDeserializer.setRemoveTypeHeaders(false);
        orderJsonDeserializer.addTrustedPackages("ru.plautskiy.projects.kafka");
        orderJsonDeserializer.setUseTypeMapperForKey(true);

        Map<String, Object> consumerProperties = this.properties.buildConsumerProperties();
        consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, orderJsonDeserializer);
        return new DefaultKafkaConsumerFactory<>(consumerProperties, new StringDeserializer(), orderJsonDeserializer);
    }


    @Bean("jsonKafkaListenerContainerFactory")
    ConcurrentKafkaListenerContainerFactory<String, Order> jsonKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Order> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(jsonKafkaConsumerFactory());
        return factory;
    }
}
