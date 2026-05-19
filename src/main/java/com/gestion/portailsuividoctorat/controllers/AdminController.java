package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Demande;
import com.gestion.portailsuividoctorat.entites.Doctorant;
import com.gestion.portailsuividoctorat.entites.Encadrant;
import com.gestion.portailsuividoctorat.repositories.DemandeRepo;
import com.gestion.portailsuividoctorat.repositories.DoctorantRepo;
import com.gestion.portailsuividoctorat.repositories.EncadrantRepo;
import com.gestion.portailsuividoctorat.repositories.UtilisateurRepo;
import com.gestion.portailsuividoctorat.services.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final EncadrantService encadrantService;
    private final DemandeService demandeService;
    private final JuryService juryService;
    private final SoutenanceService Soutservice;
    private final UtilisateurRepo utilisateurRepo;
    private final DoctorantRepo doctorantRepo;
    private final DemandeRepo demandeRepo;

    public AdminController(AdminService adminService, EncadrantService encadrantService,
                           DemandeService demandeService,
                           UtilisateurRepo utilisateurRepo,
                           DoctorantRepo doctorantRepo,
                           DemandeRepo demandeRepo,
                           JuryService juryService,
                           SoutenanceService Soutservice) {
        this.adminService = adminService;
        this.encadrantService = encadrantService;
        this.demandeService = demandeService;
        this.utilisateurRepo = utilisateurRepo;
        this.doctorantRepo = doctorantRepo;
        this.demandeRepo = demandeRepo;
        this.juryService = juryService;
        this.Soutservice = Soutservice;
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("totalUsers", utilisateurRepo.count());
        model.addAttribute("totalDoctorants", doctorantRepo.count());
        model.addAttribute("demandesEnAttente", demandeRepo.countByStatut(Demande.StatutDemande.EN_ATTENTE));
        model.addAttribute("demandesAutorisees", demandeRepo.countByStatut(Demande.StatutDemande.AUTORISEE));
        model.addAttribute("derniersDemandes",
                demandeRepo.findTop5ByStatutOrderByDateDepotDescIdDesc(Demande.StatutDemande.EN_ATTENTE));
        return "Admin/dashboard";
    }
    @GetMapping("/encadrants")
    public String showListEncadrants(Model model) {
        model.addAttribute("encadrants", encadrantService.findAllEncadrants());
        return "Admin/encadrants";
    }
    @PostMapping("/encadrants/create")
    public String createEncadrant(@ModelAttribute Encadrant encadrant, RedirectAttributes ra) {
        try {
            encadrantService.createEncadrant(encadrant);
            ra.addFlashAttribute("successMessage", "Encadrant créé avec succès !");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Erreur lors de la création : " + e.getMessage());
        }
        return "redirect:/admin/encadrants";
    }
    @GetMapping("/demandes")
    public String demandes(Model model) {
        model.addAttribute("demandes", demandeService.getAllDemandes());
        model.addAttribute("statuts", Demande.StatutDemande.values());
        return "Admin/demandes";
    }

    @PostMapping("/demandes/{id}/autoriser")
    public String autoriserDemande(@PathVariable Long id, RedirectAttributes ra) {
        return changerStatutDemande(id, Demande.StatutDemande.AUTORISEE, "Demande autorisée.", ra);
    }

    @PostMapping("/demandes/{id}/refuser")
    public String refuserDemande(@PathVariable Long id, RedirectAttributes ra) {
        return changerStatutDemande(id, Demande.StatutDemande.REFUSEE, "Demande refusée.", ra);
    }

    private String changerStatutDemande(Long id,
                                        Demande.StatutDemande statut,
                                        String message,
                                        RedirectAttributes ra) {
        try {
            Demande demande = demandeService.getDemandeById(id);
            demande.setStatut(statut);
            demandeRepo.save(demande);
            ra.addFlashAttribute("successMessage", message);
        } catch (RuntimeException e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("Utilisateur", adminService.getAllUsers());
        return "Admin/utilisateurs";
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

    @GetMapping("/doctorants")
    public String doctorants(Model model) {

        model.addAttribute("doctorants", adminService.getAllDoctorants());
        model.addAttribute("newDoctorant", new Doctorant());

        return "Admin/doctorants";
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
    @GetMapping("soutenances")
    public String list(Model model) {
        model.addAttribute("soutenances", Soutservice.getAllSoutenance());
        return "Admin/soutenances";
    }
    @GetMapping("/jury")
    public String showList(Model model) {
        model.addAttribute("jurys", juryService.findAllJuries());
        return "Admin/jury";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "auth/login";
    }
}
