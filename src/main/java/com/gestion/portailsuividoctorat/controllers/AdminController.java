package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Demande;
import com.gestion.portailsuividoctorat.repositories.DemandeRepo;
import com.gestion.portailsuividoctorat.repositories.DoctorantRepo;
import com.gestion.portailsuividoctorat.repositories.EncadrantRepo;
import com.gestion.portailsuividoctorat.repositories.UtilisateurRepo;
import com.gestion.portailsuividoctorat.services.AdminService;
import com.gestion.portailsuividoctorat.services.DemandeService;
import com.gestion.portailsuividoctorat.services.JuryService;
import com.gestion.portailsuividoctorat.services.SoutenanceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final DemandeService demandeService;
    private final SoutenanceService soutenanceService;
    private final JuryService juryService;
    private final UtilisateurRepo utilisateurRepo;
    private final DoctorantRepo doctorantRepo;
    private final EncadrantRepo encadrantRepo;
    private final DemandeRepo demandeRepo;

    public AdminController(AdminService adminService,
                           DemandeService demandeService,
                           SoutenanceService soutenanceService,
                           JuryService juryService,
                           UtilisateurRepo utilisateurRepo,
                           DoctorantRepo doctorantRepo,
                           EncadrantRepo encadrantRepo,
                           DemandeRepo demandeRepo) {
        this.adminService = adminService;
        this.demandeService = demandeService;
        this.soutenanceService = soutenanceService;
        this.juryService = juryService;
        this.utilisateurRepo = utilisateurRepo;
        this.doctorantRepo = doctorantRepo;
        this.encadrantRepo = encadrantRepo;
        this.demandeRepo = demandeRepo;
    }

    @ModelAttribute
    public void addCommonAttributes(Model model) {
        model.addAttribute("demandesEnAttente", demandeRepo.countByStatut(Demande.StatutDemande.EN_ATTENTE));
    }

    @GetMapping({"", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("totalUsers", utilisateurRepo.count());
        model.addAttribute("totalDoctorants", doctorantRepo.count());
        model.addAttribute("demandesAutorisees", demandeRepo.countByStatut(Demande.StatutDemande.AUTORISEE));
        model.addAttribute("derniersDemandes",
                demandeRepo.findTop5ByStatutOrderByDateDepotDescIdDesc(Demande.StatutDemande.EN_ATTENTE));
        return "Admin/dashboard";
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
            demandeService.changerStatut(id, statut);
            ra.addFlashAttribute("successMessage", message);
        } catch (RuntimeException e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", adminService.getAllUsers());
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
        model.addAttribute("encadrants", encadrantRepo.findAll());
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

    @GetMapping("/encadrants")
    public String encadrants(Model model) {
        model.addAttribute("encadrants", encadrantRepo.findAll());
        return "Admin/encadrants";
    }

    @GetMapping("/soutenances")
    public String soutenances(Model model) {
        model.addAttribute("soutenances", soutenanceService.getAllSoutenance());
        return "Admin/soutenances";
    }

    @GetMapping("/jury")
    public String jury(Model model) {
        model.addAttribute("jurys", juryService.findAllJuries());
        return "Admin/jury";
    }

    @GetMapping("/parametres")
    public String parametres() {
        return "Admin/parametres";
    }
}
