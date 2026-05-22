package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Doctorant;
import com.gestion.portailsuividoctorat.entites.Demande;
import com.gestion.portailsuividoctorat.entites.Utilisateur;
import com.gestion.portailsuividoctorat.repositories.DemandeRepo;
import com.gestion.portailsuividoctorat.services.DoctorantServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/doctorants")
public class DoctorantController {

    @Autowired
    private DoctorantServiceImpl doctorantService;

    @Autowired
    private DemandeRepo demandeRepo;

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
    public String saveDoctorant(@ModelAttribute Doctorant doctorant) {
        if (doctorant.getId() != null) {
            doctorantService.updateDoctorant(doctorant, doctorant.getId());
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
