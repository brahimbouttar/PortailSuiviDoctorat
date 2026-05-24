package com.gestion.portailsuividoctorat.services;

import com.gestion.portailsuividoctorat.entites.Doctorant;
import com.gestion.portailsuividoctorat.entites.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService {
    Utilisateur createUtilisateur(Utilisateur u);
    //modification
    Utilisateur updateUtilisateur(Utilisateur u, Long id);
    //lecture
    Utilisateur findUtilisateur(Long id);
    //list all
    List<Utilisateur> findAllUtilisateurs();
    Utilisateur save(Utilisateur u);
    //Suppression
    void DeleteUtilisateur(Long id);
    Optional<Utilisateur> findByEmail(String email);
    Utilisateur inscrire(Doctorant doctorant);

}
