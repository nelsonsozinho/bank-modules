package com.shire42.api.bank.config;

import com.shire42.api.bank.service.consumer.dto.LoanContractDTO;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@Log4j2
public class KafkaConsumerConfig {


    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootStrapAddress;

    @Value(value = "${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, LoanContractDTO> loansConsumerFactory(){
        log.info("Bootstrap address: {}", bootStrapAddress);
        log.info("Group ID: {}", groupId);

        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapAddress);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, LoanContractDTO> loanKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, LoanContractDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(loansConsumerFactory());
        return factory;
    }

}
