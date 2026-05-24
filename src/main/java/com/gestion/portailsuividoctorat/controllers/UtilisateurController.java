package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Utilisateur;
import com.gestion.portailsuividoctorat.services.UtilisateurServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/Utilisateur")
@Controller
public class UtilisateurController {
    @Autowired
    UtilisateurServiceImpl utilisateurService;

    @GetMapping("/form")
    public String form(@RequestParam(required = false) Long id, Model model) {
        Utilisateur utilisateur = (id != null)
                ? utilisateurService.findUtilisateur(id)
                : new Utilisateur();
        model.addAttribute("utilisateur", utilisateur);
        return "Utilisateur/Form";
    }

    @PostMapping ("/create")
    @ResponseBody
    public Utilisateur CreateUtilisateur(@RequestBody Utilisateur u) {
        return utilisateurService.createUtilisateur(u);
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Utilisateur utilisateur) {
        if (utilisateur.getId() != null) {
            utilisateurService.updateUtilisateur(utilisateur, utilisateur.getId());
        } else {
            utilisateurService.createUtilisateur(utilisateur);
        }
        return "redirect:/Utilisateur/all";
    }

    @GetMapping({"/details/{id}", "/Details/{id}"})
    public String details(Model model, @PathVariable Long id) {
        model.addAttribute("Utilisateur",utilisateurService.findUtilisateur(id));
        return "Utilisateur/Details";
    }
    @GetMapping("/all")
    public String list(Model model) {
        model.addAttribute("Utilisateur", utilisateurService.findAllUtilisateurs());
        return "Utilisateur/Liste";
    }

    @GetMapping("/update/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("utilisateur", utilisateurService.findUtilisateur(id));
        return "Utilisateur/Form";
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public void deleteUtilisateur(@PathVariable Long id){
        utilisateurService.DeleteUtilisateur(id);
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public Utilisateur UpdateUtilisateur(@PathVariable Long id, @RequestBody Utilisateur u) {
        return utilisateurService.updateUtilisateur(u,id);
    }
}
