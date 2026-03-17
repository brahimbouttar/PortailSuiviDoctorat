package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Utilisateur;
import com.gestion.portailsuividoctorat.services.UtilisateurServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
public class UtilisateurController {
    @Autowired
    UtilisateurServiceImpl utilisateurService;
    @GetMapping("/Utilisateur/{id}")
    public Utilisateur trouverUtilisateur(@PathVariable Long id){
        return utilisateurService.findUtilisateur(id);
    }
}
