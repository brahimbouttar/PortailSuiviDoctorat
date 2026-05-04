package com.gestion.portailsuividoctorat.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class DemandeEventProducer {

    private final KafkaTemplate<String, DemandeEvent> kafkaTemplate;

    public void publishDemandeEvent(DemandeEvent event) {
        String key = String.valueOf(event.getDemandeId());

        CompletableFuture<SendResult<String, DemandeEvent>> future =
                kafkaTemplate.send(KafkaConfig.TOPIC_DEMANDE, key, event);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Échec envoi Kafka pour demande {}: {}",
                        event.getDemandeId(), ex.getMessage());
            } else {
                log.info("Événement Kafka publié → topic={} partition={} offset={}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            }
        });
    }
}