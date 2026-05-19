package com.gestion.portailsuividoctorat.controllers;

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
    public String loginPage(Model model) {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes ra) {
        return utilisateurService.findByEmail(email)
                .filter(u -> u.getPassword().equals(password))
                .map(u -> {
                    session.setAttribute("user", u);
                    session.setAttribute("role", u.getRole());
                    return switch (u.getRole()) {
                        case "ADMIN"     -> "redirect:/admin";
                        case "DOCTORANT" -> "redirect:/doctorants";
                        case "ENCADRANT" -> "redirect:/encadrant";
                        default          -> "redirect:/login";
                    };
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("errorMessage", "Email ou mot de passe incorrect.");
                    return "redirect:/login";
                });
    }
}