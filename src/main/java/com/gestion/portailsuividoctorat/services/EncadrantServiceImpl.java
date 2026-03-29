package com.gestion.portailsuividoctorat.services;

import com.gestion.portailsuividoctorat.entites.Encadrant;
import com.gestion.portailsuividoctorat.repositories.EncadrantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EncadrantServiceImpl implements EncadrantService {
    @Autowired
    EncadrantRepo EncadrantRepo;
    @Override
    public Encadrant createEncadrant(Encadrant u) {
        return EncadrantRepo.save(u);
    }

    @Override
    public Encadrant updateEncadrant(Encadrant u, Long id) {
        Encadrant existing = EncadrantRepo.findById(id).orElseThrow(() -> new RuntimeException("Encadrant not found"));
        if (u.getspecialite() != null) {existing.setspecialite(u.getspecialite());}
        if (u.getgrade() != null) {existing.setgrade(u.getgrade());}
        if (u.getEtablissement) != null) {existing.setEtablissement(u.getEtablissement());}
        return EncadrantRepo.save(existing);
    }

    @Override
    public Encadrant findEncadrant(Long id) {
        return EncadrantRepo.findById(id).orElseThrow(() -> new RuntimeException("Encadrant not found"));
    }
    @Override
    public List<Encadrant> findAllEncadrants() {
        return EncadrantRepo.findAll();
    }
    @Override
    public void DeleteEncadrant(Long id) {
        Encadrant user = EncadrantRepo.findById(id).orElseThrow(() -> new RuntimeException("Encadrant not found"));
        EncadrantRepo.delete(user);
    }
}
