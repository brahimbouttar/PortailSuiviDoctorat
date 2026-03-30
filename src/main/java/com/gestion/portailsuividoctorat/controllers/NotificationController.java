package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Notification;
import com.gestion.portailsuividoctorat.services.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/api/Notification")
@RestController
@Controller
public class NotificationController {
    @Autowired
    NotificationServiceImpl NotificationService;
    @PostMapping ("/create")
    public Notification CreateNotification(@RequestBody Notificationu) {
        return NotificationService.createNotification(u);
    }
    @GetMapping("/details/{id}")
    public Notification trouverNotification@PathVariable Long id){
        return NotificationService.findNotification(id);
    }
    @GetMapping("/all")
    public List<Notification> findAllNotifications() {
        return NotificationService.findAllNotifications();
    }
    @DeleteMapping("/delete/{id}")
    public void deleteNotification(@PathVariable Long id){
        NotificationService.DeleteNotification(id);
    }
    @PutMapping("/update/{id}")
    public Notification UpdateNotification(@PathVariable Long id, @RequestBody Notification u) {
        return NotificationService.updateNotification(u,id);
    }
}
