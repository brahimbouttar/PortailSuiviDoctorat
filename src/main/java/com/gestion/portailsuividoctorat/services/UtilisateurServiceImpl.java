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
    public Utilisateur createUtilisateur(Utilisateur u) {
        return utilisateurRepo.save(u);
    }
    @Override
    public Utilisateur updateUtilisateur(Utilisateur u, Long id) {
        Utilisateur existing = utilisateurRepo.findById(id).orElseThrow(() -> new RuntimeException("Utilisateur not found"));
        if (u.getNom() != null) {existing.setNom(u.getNom());}
        if (u.getPrenom() != null) {existing.setPrenom(u.getPrenom());}
        if (u.getAdresse() != null) {existing.setAdresse(u.getAdresse());}
        if (u.getTelephone() != null) {existing.setTelephone(u.getTelephone());}
        if (u.getEmail() != null) {existing.setEmail(u.getEmail());}
        if (u.getPassword() != null) {existing.setPassword(u.getPassword());}
        return utilisateurRepo.save(existing);
    }

    @Override
    public Utilisateur findUtilisateur(Long id) {
        return utilisateurRepo.findById(id).orElseThrow(() -> new RuntimeException("Utilisateur not found"));
    }
    @Override
    public void DeleteUtilisateur(Long id) {
        Utilisateur user = utilisateurRepo.findById(id).orElseThrow(() -> new RuntimeException("Utilisateur not found"));
        utilisateurRepo.delete(user);
    }


}
