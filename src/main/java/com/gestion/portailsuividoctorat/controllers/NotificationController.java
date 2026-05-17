package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Notification;
import com.gestion.portailsuividoctorat.services.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/Notification")
@RestController
@Controller
public class NotificationController {
    @Autowired
    NotificationServiceImpl NotificationService;
    @PostMapping ("/create")
    public Notification CreateNotification(@RequestBody Notification u) {
        return NotificationService.createNotification(u);
    }
    @GetMapping("/details/{id}")
    public Notification trouverNotification(@PathVariable Long id){
        return NotificationService.findNotification(id);
    }
    @GetMapping("/all")
    public List<Notification> findAllNotifications() {
        return NotificationService.findAllNotifications();
    }
    @DeleteMapping("/delete/{id}")
    public void deleteNotification(@PathVariable Long id){
        NotificationService.deleteNotification(id);
    }
}
