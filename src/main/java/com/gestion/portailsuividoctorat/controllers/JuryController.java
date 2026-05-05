package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Jury;
import com.gestion.portailsuividoctorat.services.JuryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/jury")
public class JuryController {
    @Autowired
    JuryServiceImpl juryService;
    @GetMapping("/liste")
    public String showList(Model model) {
        model.addAttribute("jurys", juryService.findAllJuries());
        return "jury/liste";
    }
    @PostMapping("/create")
    public String createJury(@ModelAttribute Jury jury, RedirectAttributes ra) {
        try {
            juryService.createJury(jury);
            ra.addFlashAttribute("successMessage", "Jury créé avec succès !");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Erreur lors de la création : " + e.getMessage());
        }
        return "redirect:/jury/liste";
    }
    @PostMapping("/update/{id}")
    public String updateJury(@PathVariable Long id, @ModelAttribute Jury jury, RedirectAttributes ra) {
        try {
            juryService.updateJury(jury, id);
            ra.addFlashAttribute("successMessage", "Jury modifié avec succès !");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Erreur lors de la modification : " + e.getMessage());
        }
        return "redirect:/jury/liste";
    }
    @GetMapping("/delete/{id}")
    public String deleteJury(@PathVariable Long id, RedirectAttributes ra) {
        try {
            juryService.DeleteJury(id);
            ra.addFlashAttribute("successMessage", "Jury supprimé avec succès !");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Erreur lors de la suppression : " + e.getMessage());
        }
        return "redirect:/jury/liste";
    }

    @GetMapping("/details/{id}")
    @ResponseBody
    public Jury trouverJury(@PathVariable Long id) {
        return juryService.findJury(id);
    }
    @GetMapping("/all")
    @ResponseBody
    public java.util.List<Jury> findAllJurys() {
        return juryService.findAllJuries();
    }
}