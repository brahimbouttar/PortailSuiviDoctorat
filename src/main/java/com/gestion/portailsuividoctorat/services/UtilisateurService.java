package com.gestion.portailsuividoctorat.services;

import com.gestion.portailsuividoctorat.entites.Utilisateur;
import com.gestion.portailsuividoctorat.repositories.UtilisateurRepo;
import jdk.jshell.execution.Util;

public interface UtilisateurService {
    //creation
    Utilisateur createUtilisateur(Utilisateur u);
    //modification
    Utilisateur updateUtilisateur(Utilisateur u, Long id);
    //lecture
    Utilisateur findUtilisateur(Long id);
    //Suppression
    void DeleteUtilisateur(Long id);
}
