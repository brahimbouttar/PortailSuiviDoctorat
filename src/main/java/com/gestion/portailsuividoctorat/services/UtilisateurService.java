package com.gestion.portailsuividoctorat.services;

import com.gestion.portailsuividoctorat.entites.Utilisateur;
import com.gestion.portailsuividoctorat.repositories.UtilisateurRepo;

public interface UtilisateurService {
    //creation
    Utilisateur createUtilisateur(String role, String password, String telephone, String adresse, String prenom, String email, String nom);
    //modification

    //lecture
    Utilisateur findUtilisateur(Long id);
    //Suppression
}
