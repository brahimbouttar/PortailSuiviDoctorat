package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Demande;
import com.gestion.portailsuividoctorat.entites.Doctorant;
import com.gestion.portailsuividoctorat.services.DemandeService;
import com.gestion.portailsuividoctorat.services.DoctorantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/doctorants")
public class DoctorantController {

    private DoctorantServiceImpl doctorantService;
    private DemandeService service;
    public DoctorantController(DemandeService service, DoctorantServiceImpl doctorantService) {
        this.service = service;
        this.doctorantService = doctorantService;

    }
    @GetMapping
    public String listDoctorants(Model model) {
        model.addAttribute("doctorants", doctorantService.findAllDoctorants());
        model.addAttribute("newDoctorant", new Doctorant());
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
    @GetMapping("/demandes")
    public String liste(Model model) {
        model.addAttribute("doctorants", doctorantService.findAllDoctorants());
        model.addAttribute("newDoctorant", new Doctorant());
        return "Doctorant/MesDemandes";
    }

    @PostMapping("demande/create")
    public String save(@ModelAttribute Demande demande, RedirectAttributes ra) {
        try {
            service.createDemande(demande);
            ra.addFlashAttribute("successMessage", "Demande soumise avec succès !");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:demandes";
    }
    @PostMapping("/save")
    public String saveDoctorant(@ModelAttribute Doctorant doctorant) {
        if (doctorant.getId() != 0) {
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
}
