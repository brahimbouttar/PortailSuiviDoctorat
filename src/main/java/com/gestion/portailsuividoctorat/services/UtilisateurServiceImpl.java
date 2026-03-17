package com.gestion.portailsuividoctorat.services;

import com.gestion.portailsuividoctorat.entites.Utilisateur;
import com.gestion.portailsuividoctorat.repositories.UtilisateurRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
    @Autowired
    UtilisateurRepo utilisateurRepo;
    @Override
    public Utilisateur createUtilisateur(String role, String password, String telephone, String adresse, String prenom, String email, String nom) {
        return utilisateurRepo.save(new Utilisateur(role,password,telephone,adresse,prenom,email,nom));
    }

    @Override
    public Utilisateur findUtilisateur(Long id) {
        return utilisateurRepo.findById(id).get();
    }
}
