package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.services.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // ── Utilisateurs ─────────────────────────────────────────────
    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", adminService.getAllUsers());
        return "admin/users";
    }

    @PostMapping("/users/{id}/role")
    public String assignRole(@PathVariable Long id,
                             @RequestParam String role,
                             RedirectAttributes ra) {
        try {
            adminService.assignRole(id, role);
            ra.addFlashAttribute("successMessage", "Rôle assigné avec succès.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/users";
    }

    // ── Doctorants ───────────────────────────────────────────────
    @GetMapping("/doctorants")
    public String doctorants(Model model) {
        model.addAttribute("doctorants", adminService.getAllDoctorants());
        return "admin/doctorants";
    }

    @PostMapping("/doctorants/{id}/encadrant")
    public String assignEncadrant(@PathVariable Long id,
                                  @RequestParam Long encadrantId,
                                  RedirectAttributes ra) {
        try {
            adminService.assignSupervisor(id, encadrantId);
            ra.addFlashAttribute("successMessage", "Encadrant assigné avec succès.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/doctorants";
    }

    // ── Suppression via POST (pas GET) ───────────────────────────
    @PostMapping("/doctorants/{id}/delete")
    public String deleteDoctorant(@PathVariable Long id,
                                  RedirectAttributes ra) {
        try {
            adminService.deleteDoctorant(id);
            ra.addFlashAttribute("successMessage", "Doctorant supprimé.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/doctorants";
    }
}