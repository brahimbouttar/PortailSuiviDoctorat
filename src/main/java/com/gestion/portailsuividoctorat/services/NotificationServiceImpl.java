package com.gestion.portailsuividoctorat.services;

import com.gestion.portailsuividoctorat.entites.Notification;
import com.gestion.portailsuividoctorat.repositories.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    NotificationRepo NotificationRepo;
    @Override
    public Notification createNotification(Notification u) {
        return NotificationRepo.save(u);
    }

    @Override
    public Notification findNotification (Long id) {
        return NotificationRepo.findById(id).orElseThrow(() -> new RuntimeException("Notification  not found"));
    }
    @Override
    public List<Notification> findAllNotifications() {
        return NotificationRepo.findAll();
    }

    @Override
    public List<Notification> findInbox(Long userId) {
        return NotificationRepo.findByDestinataireIdOrderByDateDescIdDesc(userId);
    }

    @Override
    public List<Notification> findSent(Long userId) {
        return NotificationRepo.findByExpediteurIdOrderByDateDescIdDesc(userId);
    }

    @Override
    public long countUnread(Long userId) {
        return NotificationRepo.countByDestinataireIdAndLu(userId, "NON");
    }

    @Override
    public Notification markAsRead(Long id, Long userId) {
        Notification notification = NotificationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification  not found"));
        if (notification.getDestinataire() == null || !userId.equals(notification.getDestinataire().getId())) {
            throw new RuntimeException("Message inaccessible");
        }
        notification.setLu("OUI");
        return NotificationRepo.save(notification);
    }

    @Override
    public void deleteNotification(Long id) {
        Notification  user = NotificationRepo.findById(id).orElseThrow(() -> new RuntimeException("Notification  not found"));
        NotificationRepo.delete(user);
    }
}
