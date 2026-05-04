package com.gestion.portailsuividoctorat.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    public static final String TOPIC_SOUTENANCE = "soutenance-events";
    public static final String TOPIC_DEMANDE    = "demande-events";

    @Bean
    public NewTopic soutenanceTopic() {
        return TopicBuilder.name(TOPIC_SOUTENANCE).partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic demandeTopic() {
        return TopicBuilder.name(TOPIC_DEMANDE).partitions(1).replicas(1).build();
    }

}