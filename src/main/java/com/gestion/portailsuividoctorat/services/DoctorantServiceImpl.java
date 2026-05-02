package com.gestion.portailsuividoctorat.services;

import com.gestion.portailsuividoctorat.entites.Doctorant;
import com.gestion.portailsuividoctorat.repositories.DoctorantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DoctorantServiceImpl implements DoctorantService {
    @Autowired
    DoctorantRepo DoctorantRepo;
    @Override
    public Doctorant createDoctorant(Doctorant u) {
        return DoctorantRepo.save(u);
    }

    @Override
    public Doctorant updateDoctorant(Doctorant u, Long id) {
        Doctorant existing = DoctorantRepo.findById(id).orElseThrow(() -> new RuntimeException("Doctorant not found"));
        if (u.getSujetThese() != null) {existing.setSujetThese(u.getSujetThese());}
        if (u.getCV() != null) {existing.setCV(u.getCV());}
        if (u.getLettreMotivation() != null) {existing.setLettreMotivation(u.getLettreMotivation());}
        return DoctorantRepo.save(existing);
    }

    @Override
    public Doctorant findDoctorant(Long id) {
        return DoctorantRepo.findById(id).orElseThrow();
    }
    @Override
    public List<Doctorant> findAllDoctorants() {
        return DoctorantRepo.findAll();
    }
    @Override
    public void DeleteDoctorant(Long id) {
        Doctorant user = DoctorantRepo.findById(id).orElseThrow(() -> new RuntimeException("Doctorant not found"));
        DoctorantRepo.delete(user);
    }
}
