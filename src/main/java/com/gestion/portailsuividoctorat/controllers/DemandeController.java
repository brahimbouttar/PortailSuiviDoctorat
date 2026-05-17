package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Demande;
import com.gestion.portailsuividoctorat.services.DemandeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/demandes")
public class DemandeController {

    private final DemandeService service;

    public DemandeController(DemandeService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("demandes", service.getAllDemandes());
        model.addAttribute("statuts", Demande.StatutDemande.values());
        return "Demande/demandes";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Demande demande, RedirectAttributes ra) {
        try {
            service.createDemande(demande);
            ra.addFlashAttribute("successMessage", "Demande soumise avec succès !");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/demandes";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Demande demande, RedirectAttributes ra) {
        try {
            service.updateDemande(demande.getId(), demande);
            ra.addFlashAttribute("successMessage", "Demande modifiée avec succès !");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/demandes";
    }

    @PostMapping("/statut")
    public String changerStatut(@RequestParam Long id,
                                @RequestParam Demande.StatutDemande statut,
                                RedirectAttributes ra) {
        try {
            Demande d = service.getDemandeById(id);
            d.setStatut(statut);
            service.updateDemande(id, d);
            ra.addFlashAttribute("successMessage", "Statut mis à jour : " + statut);
        } catch (RuntimeException e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/demandes";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, RedirectAttributes ra) {
        try {
            service.deleteDemande(id);
            ra.addFlashAttribute("successMessage", "Demande supprimée.");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/demandes";
    }
}