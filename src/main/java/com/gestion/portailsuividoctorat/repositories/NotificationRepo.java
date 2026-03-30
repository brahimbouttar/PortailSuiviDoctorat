package com.gestion.portailsuividoctorat.repositories;

import com.gestion.portailsuividoctorat.entites.Notification;
import com.gestion.portailsuividoctorat.entites.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepo extends JpaRepository<Notification,Long> {
}
