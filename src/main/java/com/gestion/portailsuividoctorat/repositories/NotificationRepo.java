package com.gestion.portailsuividoctorat.repositories;

import com.gestion.portailsuividoctorat.entites.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<Notification,Long> {
    List<Notification> findByDestinataireIdOrderByDateDescIdDesc(Long destinataireId);
    List<Notification> findByExpediteurIdOrderByDateDescIdDesc(Long expediteurId);
    long countByDestinataireIdAndLu(Long destinataireId, String lu);
}
