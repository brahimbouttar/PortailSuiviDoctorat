package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Doctorant;
import com.gestion.portailsuividoctorat.services.UtilisateurService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UtilisateurService utilisateurService;

    public AuthController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes ra) {
        return utilisateurService.findByEmail(email)
                .filter(u -> u.getPassword() != null && u.getPassword().equals(password))
                .map(u -> {
                    session.setAttribute("user", u);
                    String role = u.getRole() != null ? u.getRole() : "DOCTORANT";
                    session.setAttribute("role", role);
                    return switch (role) {
                        case "ADMIN" -> "redirect:/admin/dashboard";
                        case "ENCADRANT" -> "redirect:/encadrant/dashboard";
                        case "DOCTORANT" -> "redirect:/doctorants";
                        default -> "redirect:/admin/dashboard";
                    };
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("errorMessage", "Email ou mot de passe incorrect.");
                    return "redirect:/login";
                });
    }

    @GetMapping("/inscription")
    public String inscriptionPage(Model model) {
        model.addAttribute("doctorant", new Doctorant());
        return "auth/inscription";
    }

    @PostMapping("/inscription")
    public String inscription(@ModelAttribute Doctorant doctorant,
                              RedirectAttributes ra) {
        try {
            doctorant.setRole("DOCTORANT");
            utilisateurService.inscrire(doctorant);
            ra.addFlashAttribute("successMessage",
                    "Inscription réussie ! Votre dossier est en attente de validation.");
            return "redirect:/login";
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/inscription";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
