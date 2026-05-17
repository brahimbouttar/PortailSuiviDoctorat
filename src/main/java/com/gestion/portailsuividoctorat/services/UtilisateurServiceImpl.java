package com.gestion.portailsuividoctorat.services;

import com.gestion.portailsuividoctorat.entites.Doctorant;
import com.gestion.portailsuividoctorat.entites.Utilisateur;
import com.gestion.portailsuividoctorat.repositories.DoctorantRepo;
import com.gestion.portailsuividoctorat.repositories.UtilisateurRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    UtilisateurRepo utilisateurRepo;
    DoctorantRepo  doctorantRepo;
    public UtilisateurServiceImpl(UtilisateurRepo utilisateurRepo,
                                  DoctorantRepo doctorantRepo) {
        this.utilisateurRepo = utilisateurRepo;
        this.doctorantRepo   = doctorantRepo;
    }

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
        if (u.getRole() != null) {existing.setRole(u.getRole());}
        return utilisateurRepo.save(existing);
    }
    @Override
    public Utilisateur findUtilisateur(Long id) {
        return utilisateurRepo.findById(id).orElseThrow(() -> new RuntimeException("Utilisateur not found"));
    }
    @Override
    public List<Utilisateur> findAllUtilisateurs() {
        return utilisateurRepo.findAll();
    }
    @Override
    public void DeleteUtilisateur(Long id) {
        Utilisateur user = utilisateurRepo.findById(id).orElseThrow(() -> new RuntimeException("Utilisateur not found"));
        utilisateurRepo.delete(user);
    }
    @Override
    public Optional<Utilisateur> findByEmail(String email) {
        return utilisateurRepo.findByEmail(email);
    }

    @Override
    public Utilisateur inscrire(Doctorant doctorant) {
        if (utilisateurRepo.findByEmail(doctorant.getEmail()).isPresent()) {
            throw new RuntimeException("Un compte existe déjà avec cet email.");
        }
        return doctorantRepo.save(doctorant);
    }

}
