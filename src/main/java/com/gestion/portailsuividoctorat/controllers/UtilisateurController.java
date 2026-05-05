package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Utilisateur;
import com.gestion.portailsuividoctorat.services.UtilisateurServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Utilisateur")
public class UtilisateurController {

    @Autowired
    private UtilisateurServiceImpl utilisateurService;


    @GetMapping("/all")
    public String listUtilisateurs(Model model) {
        model.addAttribute("Utilisateur",
                utilisateurService.findAllUtilisateurs());

        model.addAttribute("newUtilisateur",
                new Utilisateur());

        return "Utilisateur/Liste";
    }


    @PostMapping("/save")
    public String createUtilisateur(
            @ModelAttribute("newUtilisateur") Utilisateur utilisateur) {

        utilisateurService.createUtilisateur(utilisateur);

        return "redirect:/Utilisateur/all";
    }


    @PostMapping("/update/{id}")
    public String updateUtilisateur(
            @PathVariable Long id,
            @ModelAttribute Utilisateur utilisateur) {

        utilisateurService.updateUtilisateur(utilisateur, id);

        return "redirect:/Utilisateur/all";
    }


    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String deleteUtilisateur(@PathVariable Long id) {
        utilisateurService.DeleteUtilisateur(id);
        return "success";
    }


    @GetMapping("/details/{id}")
    public String details(
            Model model,
            @PathVariable Long id) {

        model.addAttribute("Utilisateur",
                utilisateurService.findUtilisateur(id));

        return "Utilisateur/Details";
    }
}