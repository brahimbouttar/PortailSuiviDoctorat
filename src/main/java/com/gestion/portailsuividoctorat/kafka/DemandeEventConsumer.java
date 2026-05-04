package com.gestion.portailsuividoctorat.kafka;

import com.gestion.portailsuividoctorat.entites.Demande;
import com.gestion.portailsuividoctorat.entites.Notification;
import com.gestion.portailsuividoctorat.repositories.NotificationRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class DemandeEventConsumer {

    private final NotificationRepo notificationRepo;

    @KafkaListener(
            topics = KafkaConfig.TOPIC_DEMANDE,
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )

    public void consume(DemandeEvent event) {
        if (!"ADMIN".equals(event.getRoleAction())) {
            return;
        }
        if (event.getStatut() != Demande.StatutDemande.VALIDEE_ADMIN &&
                event.getStatut() != Demande.StatutDemande.REFUSEE &&
                event.getStatut() != Demande.StatutDemande.AUTORISEE) {
            return;
        }

        Notification notification = new Notification();
        notification.setMessage(buildMessage(event));
        notification.setDate(new Date());
        notification.setLu("NON");

        notificationRepo.save(notification);
    }

    private String buildMessage(DemandeEvent event) {
        String doc = event.getNomDoctorant() != null
                ? event.getNomDoctorant()
                : "Doctorant #" + event.getDemandeId();

        return switch (event.getStatut()) {
            case EN_ATTENTE        -> "Nouvelle demande soumise par " + doc + " — en attente de validation.";
            case VALIDEE_DIRECTEUR -> "Demande de " + doc + " validée par le directeur.";
            case VALIDEE_ADMIN     -> "Demande de " + doc + " validée par l'administration.";
            case REFUSEE           -> "Demande de " + doc + " refusée." +
                    (event.getObservations() != null ? " Motif : " + event.getObservations() : "");
            case AUTORISEE         -> "Demande de " + doc + " autorisée — soutenance possible !";
        };
    }
}