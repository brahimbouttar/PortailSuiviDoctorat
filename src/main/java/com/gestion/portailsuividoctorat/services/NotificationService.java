package com.gestion.portailsuividoctorat.services;

import com.gestion.portailsuividoctorat.entites.Notification;
import com.gestion.portailsuividoctorat.entites.Utilisateur;

import java.util.List;

public interface NotificationService {
    // création
    Notification createNotification(Notification n);
    // modification
    Notification updateNotification(Notification n, Long id);
    // lecture
    Notification findNotification(Long id);
    // liste de tous
    List<Notification> findAllNotifications();
    // suppression
    void deleteNotification(Long id);
}