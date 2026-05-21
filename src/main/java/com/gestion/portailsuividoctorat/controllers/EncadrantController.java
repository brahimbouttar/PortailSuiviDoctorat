package com.gestion.portailsuividoctorat.controllers;

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

    @Autowired
    EncadrantServiceImpl encadrantService;
    @Autowired
    DoctorantRepo doctorantRepo;
    @GetMapping
    public String showList(Model model) {
        model.addAttribute("encadrants", encadrantService.findAllEncadrants());
        return "Encadrant/Liste";
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
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Encadrant> tous = encadrantService.findAllEncadrants();
        model.addAttribute("totalEncadrants", tous.size());
        model.addAttribute("totalSpecialites", tous.stream().map(Encadrant::getSpecialite).filter(s -> s != null && !s.isBlank()).distinct().count());
        model.addAttribute("totalGrades", tous.stream().map(Encadrant::getGrade).filter(s -> s != null && !s.isBlank()).distinct().count());
        model.addAttribute("totalEtablissements", tous.stream().map(Encadrant::getEtablissement).filter(s -> s != null && !s.isBlank()).distinct().count());
        return "Encadrant/Dashboard";
    }

    @GetMapping("/nouveau")
    public String nouveau() {
        return "redirect:/encadrant/liste";
    }

    @GetMapping("/listes")
    public String listes(Model model, HttpSession session) {
        model.addAttribute("doctorants", getDoctorantsEncadres(session));
        model.addAttribute("nbActif",    0);
        model.addAttribute("nbSuspendu", 0);
        model.addAttribute("nbDiplome",  0);
        model.addAttribute("nbAbandon",  0);
        return "Encadrant/Listes";
    }

    private java.util.List<com.gestion.portailsuividoctorat.entites.Doctorant> getDoctorantsEncadres(HttpSession session) {
        Object user = session.getAttribute("user");
        Long encadrantId = null;
        if (user instanceof Encadrant encadrant) {
            encadrantId = encadrant.getId();
        } else if (user instanceof Utilisateur utilisateur) {
            encadrantId = utilisateur.getId();
        }
        if (encadrantId == null) {
            return doctorantRepo.findAll();
        }
        Long finalEncadrantId = encadrantId;
        return doctorantRepo.findAll().stream()
                .filter(d -> d.getEncadrant() != null && finalEncadrantId.equals(d.getEncadrant().getId()))
                .toList();
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
