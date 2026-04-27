package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Utilisateur;
import com.gestion.portailsuividoctorat.services.UtilisateurServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/Utilisateur")
//@RestController
@Controller
public class UtilisateurController {
    @Autowired
    UtilisateurServiceImpl utilisateurService;
    @PostMapping ("/create")
    public Utilisateur CreateUtilisateur(@RequestBody Utilisateur u) {
        return utilisateurService.createUtilisateur(u);
    }
    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable Long id) {
        model.addAttribute("Utilisateur",utilisateurService.findUtilisateur(id));
        return "Utilisateur/Details";
    }
    @GetMapping("/all")
    public String list(Model model) {
        model.addAttribute("Utilisateur", utilisateurService.findAllUtilisateurs());
        return "Utilisateur/Liste";
    }
    @DeleteMapping("/delete/{id}")
    public void deleteUtilisateur(@PathVariable Long id){
        utilisateurService.DeleteUtilisateur(id);
    }
    @PutMapping("/update/{id}")
    public Utilisateur UpdateUtilisateur(@PathVariable Long id, @RequestBody Utilisateur u) {
        return utilisateurService.updateUtilisateur(u,id);
    }
}
