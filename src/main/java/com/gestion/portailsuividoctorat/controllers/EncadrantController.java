package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Encadrant;
import com.gestion.portailsuividoctorat.services.EncadrantServiceImpl;
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

    @GetMapping("/liste")
    public String showList(Model model) {
        model.addAttribute("encadrants", encadrantService.findAllEncadrants());
        return "encadrant/liste";
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
    public String updateEncadrant(@PathVariable Long id,
                                  @ModelAttribute Encadrant encadrant,
                                  RedirectAttributes ra) {
        try {
            encadrantService.updateEncadrant(encadrant, id);
            ra.addFlashAttribute("successMessage", "Encadrant modifié avec succès !");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Erreur lors de la modification : " + e.getMessage());
        }
        return "redirect:/encadrant/liste";
    }

    @PostMapping("/delete/{id}")
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
    public List<Encadrant> findAllEncadrants() {
        return encadrantService.findAllEncadrants();
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Encadrant> tous = encadrantService.findAllEncadrants();
        return "encadrant/dashboard";
    }
}