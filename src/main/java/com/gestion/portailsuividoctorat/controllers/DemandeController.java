package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Demande;
import com.gestion.portailsuividoctorat.entites.Doctorant;
import com.gestion.portailsuividoctorat.entites.Utilisateur;
import com.gestion.portailsuividoctorat.repositories.DemandeRepo;
import com.gestion.portailsuividoctorat.repositories.DoctorantRepo;
import com.gestion.portailsuividoctorat.services.DemandeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/demandes")
public class DemandeController {

    private final DemandeService service;
    private final DemandeRepo demandeRepo;
    private final DoctorantRepo doctorantRepo;

    public DemandeController(DemandeService service, DemandeRepo demandeRepo, DoctorantRepo doctorantRepo) {
        this.service = service;
        this.demandeRepo = demandeRepo;
        this.doctorantRepo = doctorantRepo;
    }

    @GetMapping
    public String list(Model model, HttpSession session) {
        Object role = session.getAttribute("role");
        if ("DOCTORANT".equals(role)) {
            return "redirect:/doctorants/demandes";
        }
        model.addAttribute("demandes", service.getAllDemandes());
        model.addAttribute("statuts", Demande.StatutDemande.values());
        return "Demande/demandes";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Demande demande, HttpSession session, RedirectAttributes ra) {
        try {
            getDoctorantConnecte(session).ifPresent(demande::setDoctorant);
            service.createDemande(demande);
            ra.addFlashAttribute("successMessage", "Demande soumise avec succes !");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/demandes";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Demande demande, HttpSession session, RedirectAttributes ra) {
        try {
            getDoctorantConnecte(session).ifPresent(demande::setDoctorant);
            service.updateDemande(demande.getId(), demande);
            ra.addFlashAttribute("successMessage", "Demande modifiee avec succes !");
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
            service.changerStatut(id, statut);
            ra.addFlashAttribute("successMessage", "Statut mis a jour : " + statut);
        } catch (RuntimeException e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/demandes";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, RedirectAttributes ra) {
        try {
            service.deleteDemande(id);
            ra.addFlashAttribute("successMessage", "Demande supprimee.");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/demandes";
    }

    private List<Demande> getDemandesVisibles(HttpSession session) {
        Object role = session.getAttribute("role");
        if ("DOCTORANT".equals(role)) {
            return getDoctorantConnecte(session)
                    .map(doctorant -> demandeRepo.findByDoctorantIdOrderByDateDepotDescIdDesc(doctorant.getId()))
                    .orElseGet(List::of);
        }
        return service.getAllDemandes();
    }

    private Optional<Doctorant> getDoctorantConnecte(HttpSession session) {
        Object user = session.getAttribute("user");
        if (user instanceof Doctorant doctorant) {
            return Optional.of(doctorant);
        }
        if (user instanceof Utilisateur utilisateur && utilisateur.getId() != null) {
            return doctorantRepo.findById(utilisateur.getId());
        }
        return doctorantRepo.findAll().stream().findFirst();
    }
}
