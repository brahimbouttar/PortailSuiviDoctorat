package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Doctorant;
import com.gestion.portailsuividoctorat.entites.Demande;
import com.gestion.portailsuividoctorat.entites.Utilisateur;
import com.gestion.portailsuividoctorat.repositories.DemandeRepo;
import com.gestion.portailsuividoctorat.services.DemandeService;
import com.gestion.portailsuividoctorat.services.DoctorantServiceImpl;
import com.gestion.portailsuividoctorat.services.SoutenanceService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/doctorants")
public class DoctorantController {

    @Autowired
    private DoctorantServiceImpl doctorantService;

    @Autowired
    private DemandeRepo demandeRepo;

    @Autowired
    private DemandeService demandeService;

    @Autowired
    private SoutenanceService soutenanceService;

    @GetMapping
    public String dashboard(Model model, HttpSession session) {
        List<Doctorant> doctorants = doctorantService.findAllDoctorants();
        Optional<Doctorant> doctorantConnecte = getDoctorantConnecte(session, doctorants);

        if (doctorantConnecte.isEmpty()) {
            model.addAttribute("doctorants", doctorants);
            model.addAttribute("newDoctorant", new Doctorant());
            model.addAttribute("messageInfo", "Aucun profil doctorant n'est disponible.");
            return "Doctorant/Dashboard";
        }

        Doctorant doctorant = doctorantConnecte.get();
        List<Demande> demandes = demandeRepo.findByDoctorantIdOrderByDateDepotDescIdDesc(doctorant.getId());

        long demandesEnAttente = demandes.stream()
                .filter(d -> d.getStatut() == Demande.StatutDemande.EN_ATTENTE)
                .count();
        long demandesAutorisees = demandes.stream()
                .filter(d -> d.getStatut() == Demande.StatutDemande.AUTORISEE)
                .count();

        model.addAttribute("doctorant", doctorant);
        model.addAttribute("doctorants", doctorants);
        model.addAttribute("demandes", demandes);
        model.addAttribute("demandesTotal", demandes.size());
        model.addAttribute("demandesEnAttente", demandesEnAttente);
        model.addAttribute("demandesAutorisees", demandesAutorisees);
        model.addAttribute("profilCompletion", calculerCompletionProfil(doctorant));
        model.addAttribute("currentPage", "dashboard");
        return "Doctorant/Dashboard";
    }

    @GetMapping("/form")
    public String showForm(@RequestParam(required = false) Long id, Model model) {
        Doctorant doctorant = (id != null)
                ? doctorantService.findDoctorant(id)
                : new Doctorant();
        model.addAttribute("doctorant", doctorant);
        return "Doctorant/Form";
    }
    @GetMapping("/liste")
    public String liste(Model model) {
        model.addAttribute("doctorants", doctorantService.findAllDoctorants());
        model.addAttribute("newDoctorant", new Doctorant());
        return "Doctorant/Liste";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam Long id, Model model) {
        model.addAttribute("editDoctorant", doctorantService.findDoctorant(id));
        model.addAttribute("doctorants", doctorantService.findAllDoctorants());
        model.addAttribute("newDoctorant", new Doctorant());
        return "Doctorant/Liste";
    }

    @PostMapping("/update/{id}")
    public String updateDoctorant(@PathVariable Long id, @ModelAttribute Doctorant doctorant) {
        doctorantService.updateDoctorant(doctorant, id);
        return "redirect:/doctorants/liste";
    }

    @PostMapping("/save")
    public String saveDoctorant(@ModelAttribute Doctorant doctorant,
                                @RequestParam(required = false) MultipartFile cvFile,
                                @RequestParam(required = false) MultipartFile lettreFile,
                                HttpSession session) throws IOException {
        if (cvFile != null && !cvFile.isEmpty()) {
            doctorant.setCV(saveFile(cvFile));
        }
        if (lettreFile != null && !lettreFile.isEmpty()) {
            doctorant.setLettreMotivation(saveFile(lettreFile));
        }
        if (doctorant.getId() != null) {
            doctorantService.updateDoctorant(doctorant, doctorant.getId());
            session.setAttribute("user", doctorantService.findDoctorant(doctorant.getId()));
        } else {
            doctorantService.createDoctorant(doctorant);
        }
        return "redirect:/doctorants";
    }

    @GetMapping("/detail")
    public String detailDoctorant(@RequestParam Long id, Model model) {
        model.addAttribute("doctorant", doctorantService.findDoctorant(id));
        return "Doctorant/Details";
    }

    @PostMapping("/delete/{id}")
    public String deleteDoctorant(@PathVariable Long id) {
        doctorantService.DeleteDoctorant(id);
        return "redirect:/doctorants";
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteDoctorantAjax(@PathVariable Long id) {
        doctorantService.DeleteDoctorant(id);
    }

    @GetMapping("/demandes")
    public String mesDemandes(Model model, HttpSession session) {
        List<Doctorant> doctorants = doctorantService.findAllDoctorants();
        getDoctorantConnecte(session, doctorants).ifPresent(d -> {
            model.addAttribute("demandes", demandeRepo.findByDoctorantIdOrderByDateDepotDescIdDesc(d.getId()));
            model.addAttribute("doctorantId", d.getId());
        });
        return "Doctorant/MesDemandes";
    }

    @PostMapping("/demande/create")
    public String createDemande(@ModelAttribute Demande demande, HttpSession session, RedirectAttributes ra) {
        try {
            List<Doctorant> doctorants = doctorantService.findAllDoctorants();
            getDoctorantConnecte(session, doctorants).ifPresent(demande::setDoctorant);
            demandeService.createDemande(demande);
            ra.addFlashAttribute("successMessage", "Demande soumise avec succès !");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/doctorants/demandes";
    }

    @PostMapping("/demande/update")
    public String updateDemande(@ModelAttribute Demande demande, HttpSession session, RedirectAttributes ra) {
        try {
            demandeService.updateDemande(demande.getId(), demande);
            ra.addFlashAttribute("successMessage", "Demande modifiée avec succès !");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/doctorants/demandes";
    }

    @GetMapping("/soutenance")
    public String maSoutenance(Model model, HttpSession session) {
        List<Doctorant> doctorants = doctorantService.findAllDoctorants();
        getDoctorantConnecte(session, doctorants).ifPresent(d -> {
            model.addAttribute("doctorantId", d.getId());
        });
        model.addAttribute("soutenances", soutenanceService.getAllSoutenance());
        return "Doctorant/Soutenance";
    }

    @PostMapping("/demande/delete")
    public String deleteDemande(@RequestParam Long id, RedirectAttributes ra) {
        try {
            demandeService.deleteDemande(id);
            ra.addFlashAttribute("successMessage", "Demande supprimée.");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/doctorants/demandes";
    }

    private String saveFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return null;
        String uploadPath = System.getProperty("user.dir") + File.separator + "uploads";
        File directory = new File(uploadPath);
        if (!directory.exists()) directory.mkdirs();
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        file.transferTo(new File(directory, fileName));
        return fileName;
    }

    private Optional<Doctorant> getDoctorantConnecte(HttpSession session, List<Doctorant> doctorants) {
        Object user = session.getAttribute("user");
        if (user instanceof Doctorant doctorant) {
            return Optional.of(doctorant);
        }
        if (user instanceof Utilisateur utilisateur) {
            return doctorants.stream()
                    .filter(d -> d.getId() != null && d.getId().equals(utilisateur.getId()))
                    .findFirst();
        }
        return doctorants.stream().findFirst();
    }

    private int calculerCompletionProfil(Doctorant doctorant) {
        int total = 8;
        int filled = 0;
        if (doctorant.getNom() != null && !doctorant.getNom().isBlank()) filled++;
        if (doctorant.getPrenom() != null && !doctorant.getPrenom().isBlank()) filled++;
        if (doctorant.getEmail() != null && !doctorant.getEmail().isBlank()) filled++;
        if (doctorant.getTelephone() != null && !doctorant.getTelephone().isBlank()) filled++;
        if (doctorant.getAdresse() != null && !doctorant.getAdresse().isBlank()) filled++;
        if (doctorant.getSujetThese() != null && !doctorant.getSujetThese().isBlank()) filled++;
        if (doctorant.getCV() != null && !doctorant.getCV().isBlank()) filled++;
        if (doctorant.getLettreMotivation() != null && !doctorant.getLettreMotivation().isBlank()) filled++;
        return (int) Math.round((filled * 100.0) / total);
    }
}
