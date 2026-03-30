package com.gestion.portailsuividoctorat.services;

import com.gestion.portailsuividoctorat.entites.Utilisateur;
import com.gestion.portailsuividoctorat.repositories.UtilisateurRepo;

import java.util.List;

public interface UtilisateurService {
    Utilisateur createUtilisateur(Utilisateur u);
    //modification
    Utilisateur updateUtilisateur(Utilisateur u, Long id);
    //lecture
    Utilisateur findUtilisateur(Long id);
    //list all
    List<Utilisateur> findAllUtilisateurs();
    //Suppression
    void DeleteUtilisateur(Long id);
}
