package com.gestion.portailsuividoctorat.services;

import com.gestion.portailsuividoctorat.entites.Doctorant;
import com.gestion.portailsuividoctorat.entites.Utilisateur;
import com.gestion.portailsuividoctorat.repositories.DoctorantRepo;
import com.gestion.portailsuividoctorat.repositories.UtilisateurRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final UtilisateurRepo utilisateurRepository;
    private final DoctorantRepo doctorantRepository;

    public AdminServiceImpl(UtilisateurRepo utilisateurRepository,
                            DoctorantRepo doctorantRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.doctorantRepository = doctorantRepository;
    }

    @Override
    public List<Utilisateur> getAllUsers() {
        return utilisateurRepository.findAll();
    }

    @Override
    public void assignRole(long userId, String role) {
        Utilisateur user = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        user.setRole(role);
        utilisateurRepository.save(user);
    }

    @Override
    public List<Doctorant> getAllDoctorants() {
        return doctorantRepository.findAll();
    }

    @Override
    public Doctorant getDoctorant(long id) {
        return doctorantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctorant non trouvé"));
    }

    @Override
    public void deleteDoctorant(long id) {
        doctorantRepository.deleteById(id);
    }

    @Override
    public void assignSupervisor(long doctorantId, long encadrantId) {
        Doctorant doctorant = doctorantRepository.findById(doctorantId)
                .orElseThrow(() -> new RuntimeException("Doctorant non trouvé"));

        Utilisateur encadrant = utilisateurRepository.findById(encadrantId)
                .orElseThrow(() -> new RuntimeException("Encadrant non trouvé"));

        doctorant.setEncadrant(encadrant);
        doctorantRepository.save(doctorant);
    }
}