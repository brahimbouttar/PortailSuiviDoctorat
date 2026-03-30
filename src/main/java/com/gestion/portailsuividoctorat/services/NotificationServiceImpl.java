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
        if (u.getmessage() != null) {existing.setmessage(u.getmessage());}
        if (u.getdate() != null) {existing.setdate(u.getdate());}
        if (u.getlu() != null) {existing.setlu(u.getlu());}
        return Notification Repo.save(existing);
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
    public void DeleteNotification(Long id) {
        Notification  user = NotificationRepo.findById(id).orElseThrow(() -> new RuntimeException("Notification  not found"));
        NotificationRepo.delete(user);
    }
}
