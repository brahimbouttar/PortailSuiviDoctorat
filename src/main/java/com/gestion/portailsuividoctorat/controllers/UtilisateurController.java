package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Utilisateur;
import com.gestion.portailsuividoctorat.services.UtilisateurServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/api/Utilisateur")
@RestController
@Controller
public class UtilisateurController {
    @Autowired
    UtilisateurServiceImpl utilisateurService;
    @PostMapping ("/create")
    public Utilisateur CreateUtilisateur(@RequestBody Utilisateur u) {
        return utilisateurService.createUtilisateur(u);
    }
    @GetMapping("/details/{id}")
    public Utilisateur trouverUtilisateur(@PathVariable Long id){
        return utilisateurService.findUtilisateur(id);
    }
    @GetMapping("/all")
    public List<Utilisateur> findAllUtilisateurs() {
        return utilisateurService.findAllUtilisateurs();
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
