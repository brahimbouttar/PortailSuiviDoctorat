package com.gestion.portailsuividoctorat.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class KafkaConfig {

    public static final String TOPIC_SOUTENANCE = "soutenance-events";
    public static final String TOPIC_DEMANDE    = "demande-events";

}