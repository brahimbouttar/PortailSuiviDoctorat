package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Notification;
import com.gestion.portailsuividoctorat.entites.Utilisateur;
import com.gestion.portailsuividoctorat.repositories.UtilisateurRepo;
import com.gestion.portailsuividoctorat.services.NotificationServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

@Controller
public class NotificationController {
    @Autowired
    NotificationServiceImpl NotificationService;

    @Autowired
    UtilisateurRepo utilisateurRepo;

    @GetMapping("/messages")
    public String messages(Model model, HttpSession session) {
        Utilisateur currentUser = getCurrentUser(session);
        if (currentUser == null || currentUser.getId() == null) {
            return "redirect:/login";
        }

        model.addAttribute("messagesRecus", NotificationService.findInbox(currentUser.getId()));
        model.addAttribute("messagesEnvoyes", NotificationService.findSent(currentUser.getId()));
        model.addAttribute("utilisateurs", utilisateurRepo.findAll().stream()
                .filter(u -> u.getId() != null && !u.getId().equals(currentUser.getId()))
                .toList());
        model.addAttribute("nonLus", NotificationService.countUnread(currentUser.getId()));

        String role = currentUser.getRole() != null ? currentUser.getRole() : String.valueOf(session.getAttribute("role"));
        return switch (role) {
            case "ADMIN" -> "Communication/Admin";
            case "ENCADRANT" -> "Communication/Encadrant";
            default -> "Communication/Doctorant";
        };
    }

    @PostMapping("/messages/send")
    public String sendMessage(@RequestParam Long destinataireId,
                              @RequestParam(required = false) String sujet,
                              @RequestParam String message,
                              HttpSession session,
                              RedirectAttributes ra) {
        try {
            Utilisateur currentUser = getCurrentUser(session);
            if (currentUser == null || currentUser.getId() == null) {
                return "redirect:/login";
            }
            Utilisateur destinataire = utilisateurRepo.findById(destinataireId)
                    .orElseThrow(() -> new RuntimeException("Destinataire introuvable"));

            Notification notification = new Notification();
            notification.setExpediteur(currentUser);
            notification.setDestinataire(destinataire);
            notification.setSujet(sujet != null && !sujet.isBlank() ? sujet : "Message");
            notification.setMessage(message);
            notification.setDate(new Date());
            notification.setLu("NON");
            NotificationService.createNotification(notification);
            ra.addFlashAttribute("successMessage", "Message envoyé avec succès.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/messages";
    }

    @PostMapping("/messages/{id}/read")
    public String markAsRead(@PathVariable Long id, HttpSession session, RedirectAttributes ra) {
        try {
            Utilisateur currentUser = getCurrentUser(session);
            if (currentUser == null || currentUser.getId() == null) {
                return "redirect:/login";
            }
            NotificationService.markAsRead(id, currentUser.getId());
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/messages";
    }

    @PostMapping("/messages/{id}/delete")
    public String deleteMessage(@PathVariable Long id, HttpSession session, RedirectAttributes ra) {
        try {
            Utilisateur currentUser = getCurrentUser(session);
            if (currentUser == null || currentUser.getId() == null) {
                return "redirect:/login";
            }
            Notification notification = NotificationService.findNotification(id);
            boolean isExpediteur = notification.getExpediteur() != null
                    && currentUser.getId().equals(notification.getExpediteur().getId());
            boolean isDestinataire = notification.getDestinataire() != null
                    && currentUser.getId().equals(notification.getDestinataire().getId());
            if (!isExpediteur && !isDestinataire) {
                throw new RuntimeException("Message inaccessible");
            }
            NotificationService.deleteNotification(id);
            ra.addFlashAttribute("successMessage", "Message supprimé.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/messages";
    }

    @PostMapping("/Notification/create")
    @ResponseBody
    public Notification CreateNotification(@RequestBody Notification u) {
        return NotificationService.createNotification(u);
    }

    @GetMapping("/Notification/details/{id}")
    @ResponseBody
    public Notification trouverNotification(@PathVariable Long id){
        return NotificationService.findNotification(id);
    }

    @GetMapping("/Notification/all")
    @ResponseBody
    public List<Notification> findAllNotifications() {
        return NotificationService.findAllNotifications();
    }

    @DeleteMapping("/Notification/delete/{id}")
    @ResponseBody
    public void deleteNotification(@PathVariable Long id){
        NotificationService.deleteNotification(id);
    }

    private Utilisateur getCurrentUser(HttpSession session) {
        Object user = session.getAttribute("user");
        if (user instanceof Utilisateur utilisateur) {
            return utilisateur;
        }
        return null;
    }
}
