package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Doctorant;
import com.gestion.portailsuividoctorat.entites.Utilisateur;
import com.gestion.portailsuividoctorat.repositories.DoctorantRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private final DoctorantRepo doctorantRepo;

    public HomeController(DoctorantRepo doctorantRepo) {
        this.doctorantRepo = doctorantRepo;
    }

    @GetMapping
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        Object role = session.getAttribute("role");
        if ("ADMIN".equals(role)) {
            return "redirect:/admin/dashboard";
        }
        if ("ENCADRANT".equals(role)) {
            return "redirect:/encadrant/dashboard";
        }
        return "redirect:/doctorants";
    }

    @GetMapping({"/dossiers", "/manuscrit", "/avancement"})
    public String parcoursDoctorant() {
        return "redirect:/doctorants";
    }

    @GetMapping("/profil")
    public String profil(HttpSession session) {
        Object user = session.getAttribute("user");
        if (user instanceof Doctorant doctorant && doctorant.getId() != null) {
            return "redirect:/doctorants/detail?id=" + doctorant.getId();
        }
        if (user instanceof Utilisateur utilisateur && utilisateur.getId() != null
                && doctorantRepo.existsById(utilisateur.getId())) {
            return "redirect:/doctorants/detail?id=" + utilisateur.getId();
        }
        return doctorantRepo.findAll().stream()
                .findFirst()
                .map(doctorant -> "redirect:/doctorants/detail?id=" + doctorant.getId())
                .orElse("redirect:/doctorants");
    }

}
