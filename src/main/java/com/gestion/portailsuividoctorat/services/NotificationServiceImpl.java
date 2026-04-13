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
    public Notification updateNotification(Notification u, Long id) {
        Notification existing = NotificationRepo.findById(id).orElseThrow(() -> new RuntimeException("Notification  not found"));
        if (u.getMessage() != null) {existing.setMessage(u.getMessage());}
        if (u.getDate() != null) {existing.setDate(u.getDate());}
        if (u.getLu() != null) {existing.setLu(u.getLu());}
        return NotificationRepo.save(existing);
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
    public void deleteNotification(Long id) {
        Notification  user = NotificationRepo.findById(id).orElseThrow(() -> new RuntimeException("Notification  not found"));
        NotificationRepo.delete(user);
    }
}
