package com.gestion.portailsuividoctorat.services;

import com.gestion.portailsuividoctorat.entites.Notification;

import java.util.List;

public interface NotificationService {
    // création
    Notification createNotification(Notification n);
    // modification

    // lecture
    Notification findNotification(Long id);
    // liste de tous
    List<Notification> findAllNotifications();
    List<Notification> findInbox(Long userId);
    List<Notification> findSent(Long userId);
    long countUnread(Long userId);
    Notification markAsRead(Long id, Long userId);
    // suppression
    void deleteNotification(Long id);
}
