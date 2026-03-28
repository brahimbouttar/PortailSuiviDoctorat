package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Utilisateur;
import com.gestion.portailsuividoctorat.services.UtilisateurServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
public class UtilisateurController {
    @Autowired
    UtilisateurServiceImpl utilisateurService;
    @PostMapping ("/Utilisateur")
    public Utilisateur CreateUtilisateur(@RequestBody Utilisateur u) {
        return utilisateurService.createUtilisateur(u);
    }
    @GetMapping("/Utilisateur/{id}")
    public Utilisateur trouverUtilisateur(@PathVariable Long id){
        return utilisateurService.findUtilisateur(id);
    }
    @DeleteMapping("/Utilisateur/{id}")
    public void deleteUtilisateur(@PathVariable Long id){
        utilisateurService.DeleteUtilisateur(id);
    }
    @PutMapping("/Utilisateur/{id}")
    public Utilisateur UpdateUtilisateur(@PathVariable Long id, @RequestBody Utilisateur u) {
        return utilisateurService.updateUtilisateur(u,id);
    }
}
