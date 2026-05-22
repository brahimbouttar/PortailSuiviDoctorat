package com.gestion.portailsuividoctorat.services;

import com.gestion.portailsuividoctorat.entites.Encadrant;
import com.gestion.portailsuividoctorat.repositories.EncadrantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EncadrantServiceImpl implements EncadrantService {

    @Autowired
    EncadrantRepo encadrantRepo;

    @Override
    public Encadrant createEncadrant(Encadrant u) {
        u.setRole("ENCADRANT");
        return encadrantRepo.save(u);
    }

    @Override
    public Encadrant updateEncadrant(Encadrant u, Long id) {
        Encadrant existing = encadrantRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Encadrant introuvable"));

        existing.setNom(u.getNom());
        existing.setPrenom(u.getPrenom());
        existing.setEmail(u.getEmail());
        existing.setTelephone(u.getTelephone());
        existing.setAdresse(u.getAdresse());
        existing.setSpecialite(u.getSpecialite());
        existing.setGrade(u.getGrade());
        existing.setEtablissement(u.getEtablissement());

        // Only update password if a new one was actually submitted
        if (u.getPassword() != null && !u.getPassword().isBlank()) {
            existing.setPassword(u.getPassword());
        }
        return encadrantRepo.save(existing);
    }
    @Override
    public Encadrant findByEmail(String email) {
        return encadrantRepo.findByEmail(email);
    }
    @Override
    public Encadrant findEncadrant(Long id) {
        return encadrantRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Encadrant introuvable"));
    }

    @Override
    public List<Encadrant> findAllEncadrants() {
        return encadrantRepo.findAll();
    }

    @Override
    public void DeleteEncadrant(Long id) {
        encadrantRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Encadrant introuvable"));
        encadrantRepo.deleteById(id);
    }
}