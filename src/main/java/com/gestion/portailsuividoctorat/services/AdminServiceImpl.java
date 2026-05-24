package com.gestion.portailsuividoctorat.services;

import com.gestion.portailsuividoctorat.entites.Doctorant;
import com.gestion.portailsuividoctorat.entites.Encadrant;
import com.gestion.portailsuividoctorat.entites.Utilisateur;
import com.gestion.portailsuividoctorat.repositories.DoctorantRepo;
import com.gestion.portailsuividoctorat.repositories.EncadrantRepo;
import com.gestion.portailsuividoctorat.repositories.UtilisateurRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final UtilisateurRepo utilisateurRepository;
    private final DoctorantRepo doctorantRepository;
    private final EncadrantRepo encadrantRepository;

    public AdminServiceImpl(UtilisateurRepo utilisateurRepository,
                            DoctorantRepo doctorantRepository,
                            EncadrantRepo encadrantRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.doctorantRepository = doctorantRepository;
        this.encadrantRepository = encadrantRepository;
    }

    @Override
    public List<Utilisateur> getAllUsers() {
        return utilisateurRepository.findAll();
    }

    @Override
    public void assignRole(long userId, String role) {
        Utilisateur user = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouve"));
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
                .orElseThrow(() -> new RuntimeException("Doctorant non trouve"));
    }

    @Override
    public void deleteDoctorant(long id) {
        if (!doctorantRepository.existsById(id)) {
            throw new RuntimeException("Doctorant non trouve");
        }
        doctorantRepository.deleteById(id);
    }

    @Override
    public void assignSupervisor(long doctorantId, Long encadrantId) {
        Doctorant doctorant = doctorantRepository.findById(doctorantId)
                .orElseThrow(() -> new RuntimeException("Doctorant non trouve"));

        if (encadrantId == null || encadrantId == 0) {
            doctorant.setEncadrant(null);
            doctorantRepository.save(doctorant);
            return;
        }

        Encadrant encadrant = encadrantRepository.findById(encadrantId)
                .orElseThrow(() -> new RuntimeException("Encadrant non trouve"));

        doctorant.setEncadrant(encadrant);
        doctorantRepository.save(doctorant);
    }
}
