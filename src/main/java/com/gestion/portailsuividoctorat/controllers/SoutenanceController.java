package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.dto.SoutenanceDTO;
import com.gestion.portailsuividoctorat.services.SoutenanceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/soutenances")
public class SoutenanceController {

    private final SoutenanceService service;

    public SoutenanceController(SoutenanceService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("soutenances", service.getAllSoutenance());
        return "Soutenance/soutenances";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute SoutenanceDTO dto,
                       RedirectAttributes ra) {
        try {
            service.createSoutenance(dto);
            ra.addFlashAttribute("successMessage", "Soutenance créée avec succès !");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Erreur lors de la création : " + e.getMessage());
        }
        return "redirect:/soutenances";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute SoutenanceDTO dto,
                         RedirectAttributes ra) {
        try {
            if (dto.getId() == null) {
                throw new RuntimeException("Identifiant de soutenance obligatoire");
            }
            service.updateSoutenance(dto.getId(), dto);
            ra.addFlashAttribute("successMessage", "Soutenance modifiée avec succès !");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Erreur lors de la modification : " + e.getMessage());
        }
        return "redirect:/soutenances";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id,
                         RedirectAttributes ra) {
        try {
            service.deleteSoutenance(id);
            ra.addFlashAttribute("successMessage", "Soutenance supprimée.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Erreur lors de la suppression : " + e.getMessage());
        }
        return "redirect:/soutenances";
    }
}
