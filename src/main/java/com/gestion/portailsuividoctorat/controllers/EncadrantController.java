package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Encadrant;
import com.gestion.portailsuividoctorat.entites.Doctorant;
import com.gestion.portailsuividoctorat.entites.Utilisateur;
import com.gestion.portailsuividoctorat.repositories.DemandeRepo;
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

    @Autowired
    EncadrantServiceImpl encadrantService;
    @Autowired
    DoctorantRepo doctorantRepo;
    @Autowired
    DemandeRepo demandeRepo;
    @GetMapping
    public String showList() {
        return "redirect:/encadrant/dashboard";
    }

    @GetMapping("/liste")
    public String liste(Model model) {
        model.addAttribute("encadrants", encadrantService.findAllEncadrants());
        return "Encadrant/Liste";
    }
    @PostMapping("/create")
    public String createEncadrant(@ModelAttribute Encadrant encadrant, RedirectAttributes ra) {
        try {
            encadrantService.createEncadrant(encadrant);
            ra.addFlashAttribute("successMessage", "Encadrant créé avec succès !");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Erreur lors de la création : " + e.getMessage());
        }
        return "redirect:/encadrant/liste";
    }
    @PostMapping("/update/{id}")
    public String updateEncadrant(@PathVariable Long id, @ModelAttribute Encadrant encadrant, RedirectAttributes ra) {
        try {
            encadrantService.updateEncadrant(encadrant, id);
            ra.addFlashAttribute("successMessage", "Encadrant modifié avec succès !");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Erreur lors de la modification : " + e.getMessage());
        }
        return "redirect:/encadrant/liste";
    }


    @GetMapping("/nouveau")
    public String nouveau() {
        return "redirect:/encadrant/liste";
    }

    @GetMapping("/dashboard")
    public String listes(Model model, HttpSession session) {
        model.addAttribute("doctorants", getDoctorantsEncadres(session));
        model.addAttribute("nbActif",    0);
        model.addAttribute("nbSuspendu", 0);
        model.addAttribute("nbDiplome",  0);
        model.addAttribute("nbAbandon",  0);
        return "Encadrant/Listes";
    }

    @GetMapping("/doctorants/{id}/dossier")
    public String dossierDoctorant(@PathVariable Long id, Model model, HttpSession session, RedirectAttributes ra) {
        Doctorant doctorant = doctorantRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctorant non trouve"));

        Long encadrantId = getEncadrantId(session);
        if (encadrantId != null
                && (doctorant.getEncadrant() == null || !encadrantId.equals(doctorant.getEncadrant().getId()))) {
            ra.addFlashAttribute("errorMessage", "Vous ne pouvez consulter que les dossiers de vos doctorants.");
            return "redirect:/encadrant/listes";
        }

        model.addAttribute("doctorant", doctorant);
        model.addAttribute("demandes", demandeRepo.findByDoctorantIdOrderByDateDepotDescIdDesc(id));
        return "Encadrant/Details";
    }

    private java.util.List<com.gestion.portailsuividoctorat.entites.Doctorant> getDoctorantsEncadres(HttpSession session) {
        Long encadrantId = getEncadrantId(session);
        if (encadrantId == null) {
            return doctorantRepo.findAll();
        }
        return doctorantRepo.findByEncadrantId(encadrantId);
    }

    private Long getEncadrantId(HttpSession session) {
        Object user = session.getAttribute("user");
        if (user instanceof Encadrant encadrant) {
            return encadrant.getId();
        }
        if (user instanceof Utilisateur utilisateur) {
            return utilisateur.getId();
        }
        return null;
    }

    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String deleteEncadrant(@PathVariable Long id, RedirectAttributes ra) {
        try {
            encadrantService.DeleteEncadrant(id);
            ra.addFlashAttribute("successMessage", "Encadrant supprimé avec succès !");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Erreur lors de la suppression : " + e.getMessage());
        }
        return "redirect:/encadrant/liste";
    }
    @GetMapping("/details/{id}")
    @ResponseBody
    public Encadrant trouverEncadrant(@PathVariable Long id) {
        return encadrantService.findEncadrant(id);
    }
    @GetMapping("/all")
    @ResponseBody
    public java.util.List<Encadrant> findAllEncadrants() {
        return encadrantService.findAllEncadrants();
    }
}
