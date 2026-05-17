package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Doctorant;
import com.gestion.portailsuividoctorat.entites.Encadrant;
import com.gestion.portailsuividoctorat.entites.Utilisateur;
import com.gestion.portailsuividoctorat.repositories.DoctorantRepo;
import com.gestion.portailsuividoctorat.services.EncadrantServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/encadrant")
public class EncadrantController {

    @Autowired EncadrantServiceImpl encadrantService;
    @Autowired DoctorantRepo doctorantRepo;

    private Encadrant getEncadrantFromSession(HttpSession session) {
        Utilisateur user = (Utilisateur) session.getAttribute("user");
        if (user == null) throw new RuntimeException("Session expirée, veuillez vous reconnecter.");
        return encadrantService.findByEmail(user.getEmail());
    }


    @GetMapping
    public String dashboard(Model model, HttpSession session) {
        Encadrant encadrant = getEncadrantFromSession(session);
        List<Doctorant> doctorants = doctorantRepo.findByEncadrantId(encadrant.getId());

        model.addAttribute("totalDoctorants", doctorants.size());
        model.addAttribute("nbActif",    countByStatut(doctorants, "ACTIF"));
        model.addAttribute("nbSuspendu", countByStatut(doctorants, "SUSPENDU"));
        model.addAttribute("nbDiplome",  countByStatut(doctorants, "DIPLOME"));
        model.addAttribute("nbAbandon",  countByStatut(doctorants, "ABANDONNE"));

        return "Encadrant/Dashboard";
    }

    @GetMapping("/doctorants")
    public String mesDoctorants(Model model, HttpSession session) {
        Encadrant encadrant = getEncadrantFromSession(session);
        List<Doctorant> doctorants = doctorantRepo.findByEncadrantId(encadrant.getId());

        model.addAttribute("doctorants", doctorants);
        model.addAttribute("nbActif",    countByStatut(doctorants, "ACTIF"));
        model.addAttribute("nbSuspendu", countByStatut(doctorants, "SUSPENDU"));
        model.addAttribute("nbDiplome",  countByStatut(doctorants, "DIPLOME"));
        model.addAttribute("nbAbandon",  countByStatut(doctorants, "ABANDONNE"));

        return "Encadrant/Listes";
    }

    @PostMapping("/update/{id}")
    public String updateEncadrant(@PathVariable Long id,
                                  @ModelAttribute Encadrant encadrant,
                                  RedirectAttributes ra) {
        try {
            encadrantService.updateEncadrant(encadrant, id);
            ra.addFlashAttribute("successMessage", "Encadrant modifié avec succès !");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Erreur : " + e.getMessage());
        }
        return "redirect:/encadrant/dashboard";
    }

    @GetMapping("/delete/{id}")
    public String deleteEncadrant(@PathVariable Long id, RedirectAttributes ra) {
        try {
            encadrantService.DeleteEncadrant(id);
            ra.addFlashAttribute("successMessage", "Encadrant supprimé avec succès !");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Erreur : " + e.getMessage());
        }
        return "redirect:/encadrant/dashboard";
    }

    @GetMapping("/details/{id}")
    @ResponseBody
    public Encadrant trouverEncadrant(@PathVariable Long id) {
        return encadrantService.findEncadrant(id);
    }

    @GetMapping("/all")
    @ResponseBody
    public List<Encadrant> findAllEncadrants() {
        return encadrantService.findAllEncadrants();
    }

    private long countByStatut(List<Doctorant> list, String statut) {
        return list.stream()
                .filter(d -> statut.equalsIgnoreCase(d.getStatut()))
                .count();
    }
}